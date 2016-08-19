package com.meizu.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by root on 14-11-26.
 */
public class AIDLService extends Service {
    private static final String TAG = "AIDLService";


    IPerson.Stub stub = new IPerson.Stub() {
        @Override
        public String greet(String someone) throws RemoteException {
            Log.i(TAG, "greet() called");
            return "hello, " + someone;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind() called");
        return stub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind() called");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() called");
    }
}
