package com.meizu.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.meizu.appcenter.R;

/**
 * Created by root on 14-11-26.
 */
public class AIDLActivity extends Activity {

    private Button mBindButton;
    private Button mUnBindButton;
    private Button mRemoteButton;

    private IPerson mPerson;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("ServiceConnection", "onServiceConnected() called");
            mPerson = IPerson.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected() called");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aidl_main);

        mBindButton = (Button)findViewById(R.id.bind);
        mUnBindButton = (Button)findViewById(R.id.unbind);
        mRemoteButton = (Button)findViewById(R.id.remote);

        mBindButton.setEnabled(true);
        mRemoteButton.setEnabled(false);
        mUnBindButton.setEnabled(false);

        mBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.AIDLService");
                bindService(intent, mConn, Context.BIND_AUTO_CREATE);

                mBindButton.setEnabled(false);
                mRemoteButton.setEnabled(true);
                mUnBindButton.setEnabled(true);

            }
        });

        mUnBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mConn);

                mBindButton.setEnabled(true);
                mRemoteButton.setEnabled(false);
                mUnBindButton.setEnabled(false);
            }
        });

        mRemoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String retVal = mPerson.greet("android");
                    Toast.makeText(AIDLActivity.this, retVal, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    Toast.makeText(AIDLActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
