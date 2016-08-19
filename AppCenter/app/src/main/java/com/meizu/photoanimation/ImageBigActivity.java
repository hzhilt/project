package com.meizu.photoanimation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.meizu.appcenter.R;
import com.meizu.loadimage.LoadImage;

/**
 * Created by root on 15-1-19.
 */
public class ImageBigActivity extends Activity {

    private static final TimeInterpolator sDecelerator = new DecelerateInterpolator();
    private static final TimeInterpolator sAccelerator = new AccelerateInterpolator();
    private static final String PACKAGE_NAME = "PhotoAnimation";
    private static final int NEWBITMAP = 1;

    private ColorDrawable mBackground;

    int mLeftDelta;
    int mTopDelta;
    float mWidthScale;
    float mHeightScale;
    private ImageView mImageView;
    private FrameLayout mTopLevelLayout;

    private int DURATION = 300;
    private Bitmap mBitmap;
    private Bitmap mLastBitmap;
    private String mUrl;

    private int mW;
    private int mH;

    int mThumbnailTop;
    int mThumbnailLeft;
    int mThumbnailWidth;
    int mThumbnailHeight;

    private boolean isAnimation = false;

    private android.os.Handler mHandlerImage=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case NEWBITMAP:
                    mLastBitmap = (Bitmap)msg.obj;
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoanimation_big);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mTopLevelLayout = (FrameLayout) findViewById(R.id.topLevelLayout);

        mBackground = new ColorDrawable(Color.WHITE);
        mTopLevelLayout.setBackground(mBackground);



        getScreenWH();

        getStartInfo();

        mBitmap = getOriginBitmap(mUrl,mW,mH);
        mImageView.setImageBitmap(mBitmap);

        Thread thread = new Thread(){
            @Override
            public void run() {

                Bitmap newBitmap = getNewBitmap();
                Message message =new Message();
                message.what = NEWBITMAP;
                message.obj = newBitmap;
                mHandlerImage.sendMessage(message);
            }
        };
        thread.start();

        if (savedInstanceState == null) {
            ViewTreeObserver observer = mImageView.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {
                    mImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    int[] screenLocation = new int[2];
                    mImageView.getLocationOnScreen(screenLocation);
                    mLeftDelta = mThumbnailLeft - screenLocation[0];
                    mTopDelta = mThumbnailTop - screenLocation[1];

                    mWidthScale = (float) mThumbnailWidth / mImageView.getWidth();
                    mHeightScale = (float) mThumbnailHeight / mImageView.getHeight();

                    startAnimation();
                    return true;
                }
            });
        }

    }

    private void getStartInfo() {
        Bundle bundle = getIntent().getExtras();
        mUrl = bundle.getString(PACKAGE_NAME + ".url");
        mThumbnailTop = bundle.getInt(PACKAGE_NAME + ".top");
        mThumbnailLeft = bundle.getInt(PACKAGE_NAME + ".left");
        mThumbnailWidth = bundle.getInt(PACKAGE_NAME + ".width");
        mThumbnailHeight = bundle.getInt(PACKAGE_NAME + ".height");
    }

    private void getScreenWH() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mW = dm.widthPixels;
        mH = dm.heightPixels;
    }

    private Bitmap getNewBitmap() {
        LoadImage loadImage = LoadImage.getInstance();
        Bitmap bitmap = loadImage.getTumb(mUrl, mThumbnailWidth, mThumbnailHeight);

        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        return loadImage.getSpecifySize(bitmap, width, height);
    }

    private Bitmap getOriginBitmap(String url,int width,int height){
        //Bitmap bitmap = null;
        LoadImage loadImage = LoadImage.getInstance();
        Bitmap nullBitmap = getNullBitmap(url,width,height);
        Bitmap bitmap = loadImage.getSmallSize(url,mThumbnailWidth,mThumbnailHeight);

        int lastWidth = nullBitmap.getWidth();
        int lastHeight = nullBitmap.getHeight();
        return loadImage.getSpecifySize(bitmap,lastWidth,lastHeight);
    }

    @Override
    public void onBackPressed() {
        if (isAnimation == true){
            finish();
            isAnimation = false;
        }
        else {
            endAnimation();
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    // use ObjectAnimation
    private void startAnimation(){

        isAnimation = true;

        mImageView.setPivotX(0);
        mImageView.setPivotY(0);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mImageView,"scaleX",mWidthScale,1);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mImageView,"scaleY",mHeightScale,1);
        ObjectAnimator animatorTx = ObjectAnimator.ofFloat(mImageView,"translationX",mLeftDelta,0);
        ObjectAnimator animatorTy = ObjectAnimator.ofFloat(mImageView,"translationY",mTopDelta,0);
        ObjectAnimator animatorBG = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.setDuration(DURATION);
        animatorSet.setInterpolator(sDecelerator);

        animatorSet.playTogether(animatorX,animatorY,animatorTx,animatorTy,animatorBG);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = false;

//                int left = mImageView.getLeft();
//                int top = mImageView.getTop();
////
//                ViewGroup.LayoutParams laParams = mImageView.getLayoutParams();
//
//                laParams.height = mH;
//                laParams.width = mW;
//                mImageView.setLayoutParams(laParams);
//
////
//                mImageView.setPivotX(0);
//                mImageView.setPivotY(0);
//////
//                mImageView.setTranslationX(left);
//                mImageView.setTranslationY(100+top);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                animation.end();
            }
        });
    }

    private void endAnimation(){

        isAnimation = true;
        mImageView.setImageBitmap(mLastBitmap);

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mImageView,"scaleX",1,mWidthScale);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mImageView,"scaleY",1,mHeightScale);
        ObjectAnimator animatorTx = ObjectAnimator.ofFloat(mImageView,"translationX",0,mLeftDelta);
        ObjectAnimator animatorTy = ObjectAnimator.ofFloat(mImageView,"translationY",0,mTopDelta);
        ObjectAnimator animatorBG = ObjectAnimator.ofInt(mBackground, "alpha", 0);

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.setDuration(DURATION);
        animatorSet.setInterpolator(sDecelerator);

        animatorSet.playTogether(animatorX,animatorY,animatorTx,animatorTy,animatorBG);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = false;
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animation.end();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                animation.cancel();
            }
        });
    }

    public Bitmap getNullBitmap(String url,float ScreenWidth,float ScreenHeight){

        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        bitmap = BitmapFactory.decodeFile(url, options);
        float h = options.outHeight;
        float w = options.outWidth;

        if(h>w && h>ScreenHeight){
            float scale = h/w;
            h = ScreenHeight;
            w = h / scale;
        }

        if(h<w && w>ScreenWidth){
            float scale = w/h;
            w = ScreenWidth;
            h = w / scale;
        }

        Bitmap newBitmap = Bitmap.createBitmap( (int)w, (int)h, Bitmap.Config.ARGB_4444 );
        return newBitmap;
    }
}
