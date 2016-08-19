package com.meizu.test;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by root on 15-1-9.
 */
public class TouchActivity extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TouchImageView img = new TouchImageView(this);
        setContentView(img);
    }
}
