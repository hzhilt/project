package com.meizu.Location;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-2-3.
 */
public class LocationActivity extends Activity {

    private TextView mTv = null;
    private String url= "http://1.cmdmac.sinaapp.com/";
    private String url189 = "http://172.16.223.189:8090/index.php/storeapi/getCitys";
    private String urlCity = "http://172.16.223.189:8090/index.php/storeapi/getCitysByProvince";
    private String urlShop = "http://172.16.223.189:8090/index.php/storeapi/getStoresByCityId";

    private static List<Shop> mShops = new ArrayList<>();
    private android.os.Handler mHandlerImage = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //mTv.setText((String)msg.obj);
                    mShops = (List<Shop>)msg.obj;
                    String string = "";
                    for(int i=0;i<mShops.size();i++){
                        Shop shop = mShops.get(i);
                        string += shop.province_name + shop.city_name+" ";
                    }
                    //Log.i("heredata",string);
                    break;
                case 2:
                    //mTv.setText((String)msg.obj);
                    mShops = (List<Shop>)msg.obj;
                    String cityString = "";
                    for(int i=0;i<mShops.size();i++){
                        Shop shop = mShops.get(i);
                        cityString += shop.province_name + shop.city_name+" ";
                    }
                    Log.i("heredata",cityString);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_main);

        mTv = (TextView)findViewById(R.id.locationCity);
    }

}

