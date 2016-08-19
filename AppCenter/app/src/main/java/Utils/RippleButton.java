package Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import com.meizu.appcenter.R;

/**
 * Created by root on 15-1-5.
 */
public class RippleButton extends Button {

    private final static int  SIMPLE_RIPPLE = 0;
    private final static int  DOUBLE_RIPPLE = 1;
    private final static int  RECTANGLE_RIPPLE = 2;

    private int WIDTH;
    private int HEIGHT;
    private int FRAME_RATE = 10;
    private int DURATION = 400;
    private int PAINT_ALPHA = 90;
    private Handler mCanvasHandler;
    private float mRadiusMax = 0;
    private boolean mAnimationRunning = false;
    private int mTimer = 0;
    private int mTimerEmpty = 0;
    private int mDurationEmpty = -1;
    private float x = -1;
    private float y = -1;
    private int mZoomDuration;
    private float mZoomScale;
    private ScaleAnimation mScaleAnimation;
    private Boolean mHasToZoom;
    private Boolean mIsCentered;
    private Integer mRippleType;
    private Paint mPaint;
    private Bitmap mOriginBitmap;
    private int mRippleColor;
    private int mRipplePadding;
    private GestureDetector mGestureDetector;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    public RippleButton(Context context) {
        super(context);
    }

    public RippleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs)
    {
        if (isInEditMode())
            return;

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        mRippleColor = typedArray.getColor(R.styleable.RippleView_rv_color, getResources().getColor(R.color.rippelColor));
        mRippleType = typedArray.getInt(R.styleable.RippleView_rv_type, 0);
        mHasToZoom = typedArray.getBoolean(R.styleable.RippleView_rv_zoom, false);
        mIsCentered = typedArray.getBoolean(R.styleable.RippleView_rv_centered, false);
        DURATION = typedArray.getInteger(R.styleable.RippleView_rv_rippleDuration, DURATION);
        FRAME_RATE = typedArray.getInteger(R.styleable.RippleView_rv_framerate, FRAME_RATE);
        PAINT_ALPHA = typedArray.getInteger(R.styleable.RippleView_rv_alpha, PAINT_ALPHA);
        mRipplePadding = typedArray.getDimensionPixelSize(R.styleable.RippleView_rv_ripplePadding, 0);
        mCanvasHandler = new Handler();
        mZoomScale = typedArray.getFloat(R.styleable.RippleView_rv_zoomScale, 1.03f);
        mZoomDuration = typedArray.getInt(R.styleable.RippleView_rv_zoomDuration, 200);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mRippleColor);
        mPaint.setAlpha(PAINT_ALPHA);
        this.setWillNotDraw(false);

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {                     //一次点击up事件，在TouchDown后又没有滑动（onScroll），又没有长按（onLongPress），然后TouchUp时触发。
                return true;                                                                                               //点击一下非常快的（不滑动）TouchUp：onDown->onSingleTapUp->onSingleTapConfirmed
            }
            @Override
            public boolean onSingleTapUp(MotionEvent e) {                                  //点击一下稍微慢点的（不滑动）Touchup：onDown->onShowPress->onSingleTapUp->onSingleTapConfirmed
                return true;
            }
        });

        this.setDrawingCacheEnabled(true);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (mAnimationRunning){
            if (DURATION <= mTimer * FRAME_RATE) {
                mAnimationRunning = false;
                mTimer = 0;
                mDurationEmpty = -1;
                mTimerEmpty = 0;
                canvas.restore();
                invalidate();
                return;
            } else{
                mCanvasHandler.postDelayed(mRunnable, FRAME_RATE);
            }
            if (mTimer == 0){
                canvas.save();
            }
            canvas.drawCircle(x, y, (mRadiusMax * (((float) mTimer * FRAME_RATE) / DURATION)), mPaint);
            mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
            if (mRippleType == DOUBLE_RIPPLE && mOriginBitmap != null && (((float) mTimer * FRAME_RATE) / DURATION) > 0.4f) {
                if (mDurationEmpty == -1){
                    mDurationEmpty = DURATION - mTimer * FRAME_RATE;
                }
                mTimerEmpty++;
                final Bitmap tmpBitmap = getCircleBitmap((int) ((mRadiusMax) * (((float) mTimerEmpty * FRAME_RATE) / (mDurationEmpty))));
                canvas.drawBitmap(tmpBitmap, 0, 0, mPaint);
                tmpBitmap.recycle();
            }
            mPaint.setColor(mRippleColor);

            if (mRippleType == DOUBLE_RIPPLE) {
                if ((((float) mTimer * FRAME_RATE) / DURATION) > 0.6f)
                    mPaint.setAlpha((int) (PAINT_ALPHA - ((PAINT_ALPHA) * (((float) mTimerEmpty * FRAME_RATE) / (mDurationEmpty)))));
                else
                    mPaint.setAlpha(PAINT_ALPHA);
            } else{
                mPaint.setAlpha((int) (PAINT_ALPHA - ((PAINT_ALPHA) * (((float) mTimer * FRAME_RATE) / DURATION))));
            }
            mTimer++;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = w;
        HEIGHT = h;

        mScaleAnimation = new ScaleAnimation(1.0f, mZoomScale, 1.0f, mZoomScale, w / 2, h / 2);
        mScaleAnimation.setDuration(mZoomDuration);
        mScaleAnimation.setRepeatMode(Animation.REVERSE);
        mScaleAnimation.setRepeatCount(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event) && !mAnimationRunning){
            if (mHasToZoom){
                this.startAnimation(mScaleAnimation);
            }
            mRadiusMax = Math.max(WIDTH, HEIGHT);
            if (mRippleType != RECTANGLE_RIPPLE){
                mRadiusMax /= 2;
            }
            mRadiusMax -= mRipplePadding;
            if (mIsCentered || mRippleType == DOUBLE_RIPPLE)   {
                this.x = getMeasuredWidth() / 2;
                this.y = getMeasuredHeight() / 2;
            }else {
                this.x = event.getX();
                this.y = event.getY();
            }
            mAnimationRunning = true;
            if (mRippleType == DOUBLE_RIPPLE && mOriginBitmap == null){
                mOriginBitmap = getDrawingCache(true);
            }
            invalidate();
            this.performClick();
        }
        return true;
    }


    private Bitmap getCircleBitmap(final int radius) {
        final Bitmap output = Bitmap.createBitmap(mOriginBitmap.getWidth(), mOriginBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect((int)(x - radius), (int)(y - radius), (int)(x + radius), (int)(y + radius));

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(x, y, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mOriginBitmap, rect, rect, paint);

        return output;
    }
}
