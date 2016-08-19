package com.meizu.weatherline;

import android.app.Activity;
import android.os.Bundle;

import com.meizu.appcenter.R;

/**
 * Created by root on 15-1-21.
 */
public class WeatherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);
    }
}
