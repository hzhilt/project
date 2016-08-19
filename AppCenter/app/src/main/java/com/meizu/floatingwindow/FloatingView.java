package com.meizu.floatingwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.meizu.appcenter.R;

/**
 * Created by root on 14-12-29.
 */
public class FloatingView extends LinearLayout {

    private Button mButton;
    private ImageView mImageView;

    public FloatingView(final Context context,AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.floatingwindow_view,this).setFocusable(true);
        mButton = (Button)findViewById(R.id.floatButton);
        mButton.setText("Fly");

        mImageView = (ImageView)findViewById(R.id.floatImage);
        mImageView.setBackgroundResource(R.drawable.ic_launcher);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("Touch","parent layout onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("Touch","parent layout onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("Touch","parent layout dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
}
