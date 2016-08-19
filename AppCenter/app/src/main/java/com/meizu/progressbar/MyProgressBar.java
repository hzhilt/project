package com.meizu.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.meizu.appcenter.R;

/**
 * Created by huangzhihao on 15-10-13.
 */
public class MyProgressBar extends View {

    private static final String TAG = "Progress";

    private Paint mPaint;

    private int mMaxRange;

    private int mTextColor;

    private float mTextSize;

    private int mRoundColor;

    private int mRoundProgressColor;

    private float mRoundWidth;

    private boolean mIsShowText;

    private int mStyle;

    private int mProgress;

    private Rect mTextBound = new Rect();
    public static final int STROKE = 0;
    public static final int FILL = 1;

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MyProgressBar);

        //自定义风格
        mRoundColor = mTypedArray.getColor(R.styleable.MyProgressBar_roundColor, Color.RED);
        mRoundProgressColor = mTypedArray.getColor(R.styleable.MyProgressBar_roundProgressColor, Color.GREEN);
        mTextColor = mTypedArray.getColor(R.styleable.MyProgressBar_textColor, Color.GREEN);
        mTextSize = mTypedArray.getDimension(R.styleable.MyProgressBar_textSize, 15);
        mRoundWidth = mTypedArray.getDimension(R.styleable.MyProgressBar_roundWidth, 5);
        mMaxRange = mTypedArray.getInteger(R.styleable.MyProgressBar_maxRange, 100);
        mIsShowText = mTypedArray.getBoolean(R.styleable.MyProgressBar_textIsDisplayable, true);
        mStyle = mTypedArray.getInt(R.styleable.MyProgressBar_style, 0);

        mTypedArray.recycle();
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MyProgressBar);

        //自定义风格
        mRoundColor = mTypedArray.getColor(R.styleable.MyProgressBar_roundColor, Color.RED);
        mRoundProgressColor = mTypedArray.getColor(R.styleable.MyProgressBar_roundProgressColor, Color.GREEN);
        mTextColor = mTypedArray.getColor(R.styleable.MyProgressBar_textColor, Color.GREEN);
        mTextSize = mTypedArray.getDimension(R.styleable.MyProgressBar_textSize, 15);
        mRoundWidth = mTypedArray.getDimension(R.styleable.MyProgressBar_roundWidth, 5);
        mMaxRange = mTypedArray.getInteger(R.styleable.MyProgressBar_maxRange, 100);
        mIsShowText = mTypedArray.getBoolean(R.styleable.MyProgressBar_textIsDisplayable, true);
        mStyle = mTypedArray.getInt(R.styleable.MyProgressBar_style, 0);

        mTypedArray.recycle();
    }


    public MyProgressBar(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getWidth() / 2;
        int radius = (int) (center - mRoundWidth / 2);

        mPaint.setColor(mRoundColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mRoundWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(center, center, radius, mPaint);
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStrokeWidth(1);
        canvas.drawLine(0,center,getWidth(),center,p);


        int percent = mProgress * 100 /mMaxRange;
        String s = percent + "%";
        float textWidth = mPaint.measureText(s);

        mPaint.getTextBounds(s, 0, s.length(), mTextBound);

        float textHeight = mTextBound.bottom - mTextBound.top;


        Log.d(TAG, "height : " + mTextBound.height() + " " + "textHeight :" + textHeight);
        Log.d(TAG, "width : " + mTextBound.width() + " " + "textWidth :" + textWidth);
        Log.d(TAG, "center : " + center);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(mTextColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();


//        textHeight = (fontMetrics.descent-fontMetrics.ascent);

        Log.d(TAG," top: " + fontMetrics.top + " " + "ascent :" + fontMetrics.ascent + " " +"descent :"+ fontMetrics.descent + " " +"bottom :"+ fontMetrics.bottom + " "+ "leading :"+ fontMetrics.leading);

        if (mIsShowText && percent != 0 && mStyle == STROKE) {
            canvas.drawText(percent + "%", center - textWidth / 2, center + textHeight/2 , mPaint);//??
        }


        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mRoundWidth);
        mPaint.setColor(mRoundProgressColor);

        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);

        switch (mStyle){
            case STROKE:
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(rectF, 0, 360 * mProgress / mMaxRange, false, mPaint);
                break;
            case FILL:
                mPaint.setStyle(Paint.Style.FILL);
                if(mProgress !=0)
                    canvas.drawArc(rectF, 0, 360 * mProgress / mMaxRange, true, mPaint);
                break;
            default:
                break;
        }

    }

    public int getmMaxRange() {
        return mMaxRange;
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public float getmTextSize() {
        return mTextSize;
    }

    public int getmRoundColor() {
        return mRoundColor;
    }

    public int getmRoundProgressColor() {
        return mRoundProgressColor;
    }

    public float getmRoundWidth() {
        return mRoundWidth;
    }

    public boolean ismIsShowText() {
        return mIsShowText;
    }

    public int getmStyle() {
        return mStyle;
    }

    public int getmProgress() {
        return mProgress;
    }

    public void setmMaxRange(int mMaxRange) {
        if(mMaxRange < 0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this.mMaxRange = mMaxRange;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public void setmRoundColor(int mRoundColor) {
        this.mRoundColor = mRoundColor;
    }

    public void setmRoundProgressColor(int mRoundProgressColor) {
        this.mRoundProgressColor = mRoundProgressColor;
    }

    public void setmRoundWidth(float mRoundWidth) {
        this.mRoundWidth = mRoundWidth;
    }

    public void setmIsShowText(boolean mIsShowText) {
        this.mIsShowText = mIsShowText;
    }

    public void setmStyle(int mStyle) {
        this.mStyle = mStyle;
    }

    public void setmProgress(int mProgress) {
        if(mProgress < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if(mProgress > mMaxRange){
            mProgress = mMaxRange;
        }
        if(mProgress <= mMaxRange){
            this.mProgress = mProgress;
            postInvalidate();
        }
    }
}
