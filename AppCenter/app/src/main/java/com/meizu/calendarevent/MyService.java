package com.meizu.calendarevent;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 15-3-21.
 */
public class MyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onEventBackgroundThread(List<Event> list){
        //Toast.makeText(this,"Toast come from Service",Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post("hello");
    }
}
