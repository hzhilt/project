package com.meizu.progressbar;

import android.app.Activity;
import android.os.Bundle;

import com.meizu.appcenter.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huangzhihao on 15-10-13.
 */
public class CustomViewActivity extends Activity {

    private int mProgress = 0;
    private CircleProgress circleProgress;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        circleProgress = (CircleProgress) findViewById(R.id.circle_progress);
        final MyProgressBar progressBar = (MyProgressBar)findViewById(R.id.progressBar);

        new Thread(new Runnable() {

            @Override
            public void run() {
                while(mProgress <= 101){
                    mProgress += 1;
                    progressBar.setmProgress(mProgress);

                    if (mProgress == 100){
                        mProgress = 0;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        circleProgress.setProgress(circleProgress.getProgress() + 1);
                    }
                });
            }
        }, 1000, 100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
