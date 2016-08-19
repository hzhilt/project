package com.meizu.paint;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-1-6.
 */
public class PaintView extends View {

    public PaintView(Context context)
    {
        super(context);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mW = dm.widthPixels;
        mH = dm.heightPixels;
        initPaint();
        initCanvas();
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        mW = dm.widthPixels;
        mH = dm.heightPixels;

        initPaint();
        initCanvas();
    }

    private float mX;
    private float mY;

    private int mW ;
    private int mH ;

    private int mode;

    private static final int MODE_ZOOM = 1;
    private static final int MODE_MOVE = 2;

    private static final int FIRST_POINT = 1;
    private static final int SECOND_POINT = 2;

    private float mLastDistance = 0;
    private float mNewDistance = 0;


    private boolean ERASE_FLAG = false;
    public Paint mPaint;
    private Path mPath;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private List<PaintPath> mSaveList = new ArrayList<>();
    private List<PaintPath> mDeleteList = new ArrayList<>();
    private Paint mBitmapPaint;
    private PaintPath mPaintPath;
    private boolean FIRST_FLAG = true;

    private float WIDTH = 20;
    private float SAVE_WIDTH = 20;
    private static int COLOR = Color.RED;

    public  void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(false);
        mPaint.setDither(true);
        mPaint.setColor(COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(WIDTH);

        mBitmapPaint = new Paint();

    }

    private void initErase(){
        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setAntiAlias(false);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(WIDTH);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//设置橡皮的交互模式
    }

    public void initCanvas(){
        setBackgroundColor(Color.BLUE);//设置背景
        mBitmap = Bitmap.createBitmap(mW,mH,Bitmap.Config.ARGB_8888);//需要显示的画布
        mCanvas = new Canvas(mBitmap);
        mCanvas.setBitmap(mBitmap);

    }

    class PaintPath{
        Paint paint;
        Path path;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(mBitmap, 0, 0, null);//把bitmap显示出来
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                touchPointerDown(event);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == 0) {
                    touchMove(event);
                }else if(mode == MODE_ZOOM){
                    touchPointerMove(event);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUP();
                invalidate();
                break;
        }
        return true;
    }

    private void touchPointerMove(MotionEvent event) {
        mNewDistance = getDistance(event);

        float d = Math.abs(mNewDistance - mLastDistance);
        if(d >= 4){

        }


    }

    public float getDistance(MotionEvent event){
        float x1 = event.getX(FIRST_POINT);
        float y1 = event.getY(FIRST_POINT);

        float x2 = event.getX(SECOND_POINT);
        float y2 = event.getY(SECOND_POINT);

        return (float)Math.sqrt(Math.abs(x1-x2)*Math.abs(x1-x2)+Math.abs(y1-y2)*Math.abs(y1-y2));
    }

    private void touchPointerDown(MotionEvent event) {
        if(event.getPointerCount() == 2){
            mode = MODE_ZOOM;
            mLastDistance = getDistance(event);
        }
    }

    private void touchDown(MotionEvent event)
    {
        if(ERASE_FLAG)
            initErase();
        else
            initPaint();
        mPath = new Path();
        mPaintPath = new PaintPath();
        mPaintPath.path = mPath;
        mPaintPath.paint = mPaint;
        float x = event.getX();
        float y = event.getY();

        mX = x;
        mY = y;
        mPaintPath.path.moveTo(x, y);
    }

    private void touchMove(MotionEvent event)
    {
        final float x = event.getX();
        final float y = event.getY();
        final float previousX = mX;
        final float previousY = mY;
        final float dx = Math.abs(x - previousX);
        final float dy = Math.abs(y - previousY);

        if (dx >= 4 || dy >= 4)
        {
            float cX = (x + previousX) / 2;
            float cY = (y + previousY) / 2;
            mPaintPath.path.quadTo(previousX, previousY, cX, cY);
            mX = x;
            mY = y;
        }
        mCanvas.drawPath(mPaintPath.path, mPaintPath.paint);

    }

    private void touchUP() {
        mCanvas.drawPath(mPaintPath.path, mPaintPath.paint);
        mSaveList.add(mPaintPath);
    }

    public void undo(){
        if(mSaveList.size() > 0) {
            initCanvas();
            int position = mSaveList.size() - 1;
            PaintPath tmpPath = mSaveList.remove(position);
            mDeleteList.add(tmpPath);

            for (PaintPath pp : mSaveList) {
                mCanvas.drawPath(pp.path, pp.paint);
            }
            invalidate();
        }
    }

    public void redo(){
        if(mDeleteList.size()>0) {
            int position = mDeleteList.size() - 1;
            PaintPath tmpPath = mDeleteList.remove(position);
            mSaveList.add(tmpPath);
            mCanvas.drawPath(tmpPath.path, tmpPath.paint);
            invalidate();
        }
    }

    public void clear(){
        initCanvas();
        mSaveList.clear();
        mDeleteList.clear();
        FIRST_FLAG = true;
        ERASE_FLAG =false;
        invalidate();
    }

    public void erase(){
        ERASE_FLAG = true;
        SAVE_WIDTH = mPaint.getStrokeWidth();
    }

    public void pen(){
        ERASE_FLAG =false;
        SAVE_WIDTH = mPaint.getStrokeWidth();
    }

    public void setColor(int color){
        COLOR = color;
    }

    public float getWidthFromView(){
        return SAVE_WIDTH;
    }

    public void setWidth(int width){
        WIDTH = width;
    }


}




