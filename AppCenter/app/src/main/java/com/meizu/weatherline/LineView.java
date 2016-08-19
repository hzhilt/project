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
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-1-23.
 */
public class LineView extends View {
    private static final int SIX = 6;
    private static final int TOP = 1000;

    public LineView(Context context) {
        super(context);
        mContext = context;
        getScreenWH();
        initPaint();
        initCanvas();
        initData();
        initLayout(mStartX,mStartY);
        initAnimation(0,1);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getScreenWH();
        initPaint();
        initCanvas();
        initData();
        initLayout(mStartX,mList.get(0));
        initAnimation(0,1);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getScreenWH();
        initPaint();
        initCanvas();
        initData();
        initLayout(mStartX,mStartY);
        initAnimation(0,1);
    }

    private int[] mStartX = new int[7];//x-axis

    private int[] mStartY = new int[7];//weatherInfo
    private int[] mLastY = new int[7];
    private int[] mNextY = new int[7];
    private int[] mEndY  = new int[7];// case null;

    private List<int[]> mList = new ArrayList<>();

    private final int FIRST_X = 29;

    private int mW ;
    private int mH ;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    private int WIDTH = 5;

    private Context mContext;

    private ValueAnimator mValueAnimator;

    private void getScreenWH(){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mW = dm.widthPixels;
        mH = dm.heightPixels;
    }

    public  void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(WIDTH);
    }

    public void initCanvas(){
        //setBackgroundColor(Color.WHITE);//设置背景
        //setAlpha(255);
        mBitmap = Bitmap.createBitmap(mW, mH, Bitmap.Config.ARGB_8888);//需要显示的画布
        mCanvas = new Canvas(mBitmap);
        mCanvas.setBitmap(mBitmap);
    }

    public void initData(){

        int delta = (mW - 2*FIRST_X)/SIX;
        for(int i=0;i<7;i++)
        {
            mStartX[i] = FIRST_X + i*delta;
        }

        mStartY = new int[]{900,852,950,888,756,687,800};
        mNextY = new int[]{850,600,900,800,860,730,650,};
        mLastY = new int[]{800,564,897,765,965,800,600};
        mEndY = new int[]{1000,1000,1000,1000,1000,1000,1000};

        mList.add(mStartY);
        mList.add(mNextY);
        mList.add(mLastY);
        mList.add(mEndY);
    }

    private void initLayout(int[] x,int[] y) {

        int[] mX = x;
        int[] mY = y;
        MyPath path = new MyPath();
        path.moveTo(0,1000);
        path.lineTo(mH,1000);
        mCanvas.drawPath(path,mPaint);

        path = new MyPath();
        path.moveTo(mX[0],mY[0]);
        // draw bezier line
        for(int i = 0;i<6;i++){
            path.myQuadTo(mX[i],mY[i],mX[i+1],mY[i+1]);
        }

        mCanvas.drawPath(path,mPaint);

        path.reset();
        // draw line between the line
        for(int j = 0;j<7;j++){
            path.myCircleLineto(mX[j],mY[j],mX[j],TOP);
        }

        mCanvas.drawPath(path,mPaint);
        invalidate();
    }


    private void initAnimation(final int start,final int last) {
        mValueAnimator = new ValueAnimator();
        mValueAnimator.setDuration(2000);
        mValueAnimator.setObjectValues(new int[7]);

        mValueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                int[] x = new int[7];
                for(int i = 0;i<7;i++)
                {
                    x[i] = mList.get(start)[i] + (int) ((mList.get(last)[i] - mList.get(start)[i]) * fraction);
                }

                return x;
            }
        });

        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int[] x = (int[])animation.getAnimatedValue();
                initCanvas();
                initLayout(mStartX,x);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);//把bitmap显示出来
    }



    public void setCurrentPosition(long time,int position){

        initAnimation(position,position+1);
        mValueAnimator.setCurrentPlayTime(time);


    }
}
