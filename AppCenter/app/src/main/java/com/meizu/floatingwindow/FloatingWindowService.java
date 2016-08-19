package com.meizu.floatingwindow;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.meizu.appcenter.R;

/**
 * Created by root on 14-12-26.
 */
public class FloatingWindowService extends Service {

    private boolean mIsAdded = false;
    private WindowManager mWM;
    private WindowManager.LayoutParams mParams;
    private View mView;
    private static final float SCREEN_HALF_WIDTH = 600f;
    private static final float SCREEN_HALF_HEIGHT = 950f;


    @Override
    public void onCreate() {
        super.onCreate();
        if(!mIsAdded) {
            openFloatingWindow();
        }
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("there", "onDestroy()");
        super.onDestroy();
        if(mIsAdded) {
            closeFloatingWindow();
        }
    }

    private void openFloatingWindow() {
        mView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.floatingwindow_item,null);

        mWM = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        setParams();

        mView.setOnTouchListener(new View.OnTouchListener() {
            int lastX,lastY;
            int paramX,paramY;

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = mParams.x;
                        paramY = mParams.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) event.getRawX() - lastX;
                        int dy = (int) event.getRawY() - lastY;
                        mParams.x = paramX + dx;
                        mParams.y = paramY + dy;
                        mWM.updateViewLayout(mView, mParams);
                        break;
                    case MotionEvent.ACTION_UP:
                        setMoveTo(mParams);
                }
                return false;
            }
        });

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FloatingWindowService.this, "you click", Toast.LENGTH_SHORT).show();
            }
        });
        mWM.addView(mView, mParams);
        mIsAdded = true;
    }

    private void setMoveTo(WindowManager.LayoutParams params) {
        final ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(500);
        final float x = params.x;
        float temp = params.y;

        temp = checkY(temp);
        final float y = temp;
        valueAnimator.setObjectValues(new PointF(0,0));
        valueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {

                PointF pointF = new PointF();
                if(x < 0) {
                    pointF.x = x - x * fraction - SCREEN_HALF_WIDTH * fraction;
                    pointF.y = y;
                }
                else {
                    pointF.x = x - x * fraction + SCREEN_HALF_WIDTH * fraction;
                    pointF.y = y;
                }
                return pointF;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF)valueAnimator.getAnimatedValue();
                mParams.x = (int)pointF.x;
                mParams.y = (int)pointF.y;
                mWM.updateViewLayout(mView, mParams);
            }
        });

    }

    private float checkY(float y) {
        if(y < -SCREEN_HALF_HEIGHT) {
            return -SCREEN_HALF_HEIGHT;
        }
        else if(y > SCREEN_HALF_HEIGHT) {
            return SCREEN_HALF_HEIGHT;
        }
        return y;
    }

    private void setParams() {
        mParams = new WindowManager.LayoutParams();

        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mParams.format = PixelFormat.RGBA_8888;

        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mParams.gravity = Gravity.CENTER;
    }

    private void closeFloatingWindow() {
        mWM.removeView(mView);
        mIsAdded = false;
    }


}
