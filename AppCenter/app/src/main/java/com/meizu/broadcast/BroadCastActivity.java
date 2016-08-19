package com.meizu.broadcast;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.meizu.appcenter.R;

/**
 * Created by root on 15-1-28.
 */
public class BroadCastActivity extends Activity {

    private Button mButton;
    private MyBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_main);

        mButton = (Button)findViewById(R.id.broadcast);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.meizu.app.Broadcast");
                intent.putExtra("msg", "huangzhihao");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter("com.meizu.app.Broadcast");
        registerReceiver(mReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }
}
