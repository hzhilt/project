package com.meizu.contentprovider;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.INotificationSideChannel;
import android.widget.Toast;

/**
 * Created by root on 14-11-28.
 */
public class MessengerService  extends Service{


    static final int MSG_HELLO = 1;

    class InComeHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            if (msg.what == MSG_HELLO){
                Toast.makeText(MessengerService.this,"hello,I'm messenger!",Toast.LENGTH_LONG).show();
            }
        }
    }

    final Messenger mMessenger = new Messenger(new InComeHandler());

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }
}
