package com.meizu.sensor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.meizu.appcenter.R;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;


/**
 * Created by huangzhihao on 16-3-18.
 */
public class ToolActivity extends Activity{

    TextView mPressureVal;
    TextView mAltitude;
    TextView mGpsAltitude;
    private SensorManager sensorManager = null;
    SensorEventListener pressureListener;
    Sensor mPressure;
    float currentPressure;
    private float Calibrate = 0;

    //
    LocationManager mLocationManager;
    SimpleLocationListener mLocationListener;
    String mLocationProvider ;

    private LocationProvider _locationProvider;

    @SuppressWarnings("static-access")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        mPressureVal = (TextView) findViewById(R.id.textView1);
        mAltitude = (TextView) findViewById(R.id.textView2);
        mGpsAltitude = (TextView) findViewById(R.id.textView3);

        sensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);
        mPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        mLocationManager = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        mLocationProvider = mLocationManager.getBestProvider(criteria, true);


        _locationProvider = mLocationManager.getProvider(LocationManager.GPS_PROVIDER);
        Log.i("ToolActivity","Location is support altitude : " + _locationProvider.supportsAltitude() );


        WeakReference<ToolActivity> weakReference = new WeakReference<>(this);
        mLocationListener = new SimpleLocationListener(weakReference);

        // 获取位置信息
        // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
//        Location location = mLocationManager.getLastKnownLocation(mLocationProvider);
//        Log.i("ToolActivity","Location has altitude : " + location.hasAltitude() );
//        updateLocation(location);


        mLocationManager.requestLocationUpdates(mLocationProvider, 1000, 1, mLocationListener);

        if(mPressure == null){
            mPressureVal.setText("there is no sensor");
            return;
        }
        //Sensor mAccelerate = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        pressureListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                // TODO Auto-generated method stub
                if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                    DecimalFormat df = new DecimalFormat("0.00");
                    df.getRoundingMode();
                    currentPressure = Float.parseFloat(df.format(event.values[0]-Calibrate));
                    mPressureVal.setText(currentPressure+" mbar");

                    // h = 44330000 * (1- (P/P0)^(1/5255)) h  单位为米 P0 = 1013.25
                    double height = 44330000*(1-(Math.pow((Double.parseDouble(df.format(currentPressure))/1013.25),
                            (float)1.0/5255.0)));
                    mAltitude.setText(df.format(height)+" m");
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub

            }
        };

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        sensorManager.registerListener(pressureListener, mPressure,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(pressureListener!=null){
            sensorManager.unregisterListener(pressureListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tool_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.on://¿ªÊ¼
                sensorManager.registerListener(pressureListener, mPressure,
                        SensorManager.SENSOR_DELAY_NORMAL);
                break;
            case R.id.off://½áÊø
                if(pressureListener!=null)
                    sensorManager.unregisterListener(pressureListener);
                break;
            case R.id.Calibrate://Ð£Õý
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
                LayoutInflater inflater = (LayoutInflater) this.getSystemService(ToolActivity.this.LAYOUT_INFLATER_SERVICE);
                LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.dialog_tool, null);
                dialog.setView(layout);
                dialog.getWindow().setWindowAnimations(R.style.DialogAnimationStyle);
                dialog.show();
                final EditText et = (EditText) dialog.getWindow().findViewById(R.id.et_value);
                Button close_bt = (Button)dialog.getWindow().findViewById(R.id.close_bt);
                close_bt.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button sure_bt = (Button) dialog.getWindow().findViewById(R.id.sure_bt);
                sure_bt.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        String string =et.getText().toString();
                        if(string == null)
                            dialog.dismiss();
                        Calibrate = currentPressure - Float.parseFloat(string);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.default_setting://ÖØÖÃ
                Calibrate = 0;
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private static class SimpleLocationListener implements LocationListener {
        WeakReference<ToolActivity> mWeakReference = null;

        public SimpleLocationListener(WeakReference<ToolActivity> weakReference) {
            this.mWeakReference = weakReference;
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.i("ToolActivity","onLocationChanged ");
            ToolActivity activity = mWeakReference.get();
            activity.updateLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i("ToolActivity","onStatusChanged ");
            ToolActivity activity = mWeakReference.get();
            if (status != LocationProvider.OUT_OF_SERVICE) {
                activity.updateLocation(activity.mLocationManager.getLastKnownLocation(provider));
            } else {
                activity.updateLocation(activity.mLocationManager.getLastKnownLocation(activity.mLocationProvider));
            }
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private void updateLocation(Location location) {
        if (location == null) {
            return;
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();
        mGpsAltitude.setText("h = " + altitude + "m" + " nw = " + latitude + " " + "sj" + longitude );
    }
}
