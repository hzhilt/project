package com.meizu.floatingwindow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 14-12-26.
 */
public class FloatingWindowActivity extends Activity implements View.OnClickListener{

    private Button mOpenButton;
    private Button mCloseButton;
    private Button mCalc;
    private EditText mInput;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floatingwindow_main);

        mOpenButton = (Button)findViewById(R.id.openButton);
        mCloseButton = (Button)findViewById(R.id.closeButton);
        mCalc = (Button)findViewById(R.id.calc);
        mInput = (EditText) findViewById(R.id.input_re);
        mResult = (TextView) findViewById(R.id.result);

        mOpenButton.setOnClickListener(this);
        mCloseButton.setOnClickListener(this);

        mInput.setText("auser1 home1b");

        mCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> strings = new ArrayList<String>();
                String s = mInput.getText().toString();
//                String[] strings = s.split("(?m)^.*$")


                Matcher m = Pattern.compile("(?m)^.*$").matcher(s);


                while (m.find()){
                    strings.add(m.group());
                }

                if (strings.size() == 0){
                    mResult.setText("null");
                    return ;
                }
                if(strings.size() == 1){
                    mResult.setText(strings.get(0) );
                }else {
                    mResult.setText(strings.get(0) + "-----" + strings.get(1) + "----"+strings.get(2));
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.openButton:
                //openFloatingWindow();

                Intent startIntent = new Intent();
                startIntent.setClass(FloatingWindowActivity.this,FloatingWindowService.class);
                startService(startIntent);
                break;
            case R.id.closeButton:
                Log.i("here","ok");

                //closeFloatingWindow();
                Intent stopIntent = new Intent();
                stopIntent.setClass(FloatingWindowActivity.this,FloatingWindowService.class);
                stopService(stopIntent);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
