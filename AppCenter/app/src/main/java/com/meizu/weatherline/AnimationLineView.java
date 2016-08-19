package com.meizu.weatherline;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by root on 15-1-21.
 */
public class AnimationLineView extends View {

    private static final int SIX = 6;
    private static final int TOP = 1000;

    public AnimationLineView(Context context) {
        super(context);
        mContext = context;
        getScreenWH();
        initPaint();
        initCanvas();
        initData();
        initLayout(mStartX, mStartY);
    }


    public AnimationLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getScreenWH();
        initPaint();
        initCanvas();
        initData();
        initLayout(mStartX, mStartY);
    }

    public AnimationLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getScreenWH();
        initPaint();
        initCanvas();
        initData();
        initLayout(mStartX, mStartY);

    }

    private int[] mStartX = new int[7];
    private int[] mStartY = new int[7];
    private int[] mLastY = new int[7];


    private final int FIRST_X = 29;


    private int mW;
    private int mH;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    private int WIDTH = 2;

    private Context mContext;

    private float startX = 0;
    private float endX = 0;

    private float fraction = 0;

    private boolean isLeft = true;
    private boolean isRight = false;

    private void getScreenWH() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mW = dm.widthPixels;
        mH = dm.heightPixels;
    }

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(WIDTH);
    }

    public void initCanvas() {
        setBackgroundColor(Color.WHITE);//设置背景
        mBitmap = Bitmap.createBitmap(mW, mH, Bitmap.Config.ARGB_8888);//需要显示的画布
        mCanvas = new Canvas(mBitmap);
        mCanvas.setBitmap(mBitmap);
    }

    public void initData() {

        int delta = (mW - 2 * FIRST_X) / SIX;

        for (int i = 0; i < 7; i++) {
            mStartX[i] = FIRST_X + i * delta;
        }

        mStartY = new int[]{900, 852, 950, 888, 756, 687, 800};
        mLastY = new int[]{800, 564, 897, 765, 965, 800, 600};
    }

    private void initLayout(int[] x, int[] y) {

        int[] mX = x;
        int[] mY = y;
        MyPath path = new MyPath();
        path.moveTo(0, 1000);
        path.lineTo(mH, 1000);
        mCanvas.drawPath(path, mPaint);

        path = new MyPath();

        path.moveTo(mX[0], mY[0]);

        path.myQuadTo(mX[0], mY[0], mX[1], mY[1]);
        path.myQuadTo(mX[1], mY[1], mX[2], mY[2]);
        path.myQuadTo(mX[2], mY[2], mX[3], mY[3]);
        path.myQuadTo(mX[3], mY[3], mX[4], mY[4]);
        path.myQuadTo(mX[4], mY[4], mX[5], mY[5]);
        path.myQuadTo(mX[5], mY[5], mX[6], mY[6]);

        mCanvas.drawPath(path, mPaint);

        path.reset();

        path.myCircleLineto(mX[0], mY[0], mX[0], TOP);
        path.myCircleLineto(mX[1], mY[1], mX[1], TOP);
        path.myCircleLineto(mX[2], mY[2], mX[2], TOP);
        path.myCircleLineto(mX[3], mY[3], mX[3], TOP);
        path.myCircleLineto(mX[4], mY[4], mX[4], TOP);
        path.myCircleLineto(mX[5], mY[5], mX[5], TOP);
        path.myCircleLineto(mX[6], mY[6], mX[6], TOP);

        mCanvas.drawPath(path, mPaint);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);//把bitmap显示出来
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            //down 的时候记录位置
            case MotionEvent.ACTION_DOWN:

                float x = event.getRawX();
                startX = x;
                break;

            //移动判断左右方向
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getRawX();
                endX = x1;

                if (endX - startX < 0 && (isLeft == true)) {
                    break;
                }

                if (endX - startX > 0 && (isRight == true)) {
                    break;
                }
                //fraction，用于做动画的速率
                fraction = Math.abs(endX - startX) / 1000;

                if (fraction > 1) {
                    fraction = 1;
                }

                if (fraction < 0)
                    fraction = 0;


                int[] newX = new int[7];
                for (int i = 0; i < 7; i++) {
                    if (isLeft == true) {
                        newX[i] = mStartY[i] + (int) ((mLastY[i] - mStartY[i]) * fraction);
                    } else {
                        newX[i] = mLastY[i] + (int) ((mStartY[i] - mLastY[i]) * fraction);
                    }
                }
                initCanvas();
                initLayout(mStartX, newX);

                break;
            //判断up的位置，继续做动画
            case MotionEvent.ACTION_UP:

                if ((endX - startX > 0) && (isRight == false)) {
                    if (fraction > 0.5) {
                        runAnimation(2000);
                        isRight = true;
                        isLeft = false;
                    }
                    if (fraction < 0.5) {
                        runAnimation(0);
                        isRight = false;
                        isLeft = true;
                    }
                }
                if ((endX - startX < 0) && (isLeft == false)) {
                    if (fraction > 0.5) {
                        backAnimation(2000);
                        isLeft = true;
                        isRight = false;
                    }
                    if (fraction < 0.5) {
                        backAnimation(0);
                        isLeft = false;
                        isRight = true;
                    }
                }
                break;
        }
        return true;
    }

    private void runAnimation(long fraction) {

        isLeft = false;
        isRight = true;

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2000);
        valueAnimator.setObjectValues(new int[7]);
        //valueAnimator.setCurrentPlayTime(playTime);

        valueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                int[] x = new int[7];
                for (int i = 0; i < 7; i++) {
                    x[i] = mStartY[i] + (int) ((mLastY[i] - mStartY[i]) * fraction);
                }

                return x;
            }
        });
        //valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int[] x = (int[]) animation.getAnimatedValue();
                //mStartY = x;
                initCanvas();
                initLayout(mStartX, x);
            }
        });

        valueAnimator.setCurrentPlayTime(fraction);
    }

    private void backAnimation(long fraction) {

        isLeft = true;
        isRight = false;

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(2000);
        valueAnimator.setObjectValues(new int[7]);


        valueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                int[] x = new int[7];
                for (int i = 0; i < 7; i++) {
                    x[i] = mLastY[i] + (int) ((mStartY[i] - mLastY[i]) * fraction);
                }

                return x;
            }
        });
        //valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int[] x = (int[]) animation.getAnimatedValue();
                //mStartY = x;
                initCanvas();
                initLayout(mStartX, x);
            }
        });
        valueAnimator.setCurrentPlayTime(fraction);
    }

}
