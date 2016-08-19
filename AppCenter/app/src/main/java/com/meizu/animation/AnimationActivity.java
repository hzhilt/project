package com.meizu.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.meizu.appcenter.R;

/**
 * Created by root on 15-1-13.
 */
public class AnimationActivity extends Activity implements View.OnClickListener {

    private Button mScaleButton;
    private Button mAlphaButton;
    private Button mRotateButton;
    private Button mTranslateButton;
    private Button mBallButton;

    private ImageView mImageView;
    private ImageView mBall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_main);

        mScaleButton = (Button) findViewById(R.id.scale);
        mAlphaButton = (Button) findViewById(R.id.alpha);
        mRotateButton = (Button) findViewById(R.id.rotate);
        mTranslateButton = (Button) findViewById(R.id.translate);
        mBallButton = (Button) findViewById(R.id.ballButton);

        mBall = (ImageView) findViewById(R.id.ball);
        mImageView = (ImageView) findViewById(R.id.animation);

        mScaleButton.setOnClickListener(this);
        mTranslateButton.setOnClickListener(this);
        mRotateButton.setOnClickListener(this);
        mAlphaButton.setOnClickListener(this);
        mBallButton.setOnClickListener(this);

        //ViewPropertyAnimator
//        ValueAnimator;
//        ObjectAnimator;
//        TimeInterpolator;
//        AccelerateDecelerateInterpolator;
//        AccelerateInterpolator;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scale:
                setScaleAnimation();
                break;
            case R.id.alpha:
                setAlphaAnimation();
                break;
            case R.id.rotate:
                setRotateAnimation();
                break;
            case R.id.translate:
                setTranslateAnimation();
                break;
            case R.id.ballButton:
                setBallAnimation();
                break;
        }
    }

    private void setBallAnimation() {
        final ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(5000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        //valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                PointF pointF = new PointF();

                pointF.x = 100 * fraction * 3;
                pointF.y = (float) 0.5 * 200 * fraction * 3 * fraction * 3;
                return pointF;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                mBall.setX(pointF.x);
                mBall.setY(pointF.y);
            }
        });
    }

    private void setTranslateAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.5f);
        translateAnimation.setDuration(1000);
        animationSet.addAnimation(translateAnimation);
        mImageView.startAnimation(animationSet);
    }

    private void setRotateAnimation() {
//        AnimationSet animationSet = new AnimationSet(true);
//        RotateAnimation rotateAnimation = new RotateAnimation(0,360,
//                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        rotateAnimation.setDuration(1000);
//        animationSet.addAnimation(rotateAnimation);
//        mImageView.startAnimation(animationSet);

        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(.5f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 90f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        ObjectAnimator rotationAnim = ObjectAnimator.ofPropertyValuesHolder(mRotateButton, pvhRotation);
        rotationAnim.setDuration(5000);
        rotationAnim.start();

        ObjectAnimator rotationAnim1 = ObjectAnimator.ofPropertyValuesHolder(mImageView, pvhRotation);
        rotationAnim1.setDuration(5000);
        rotationAnim1.start();

//        ValueAnimator valueAnimator = new ValueAnimator();
//        valueAnimator.ofFloat(0f,1f);
//        valueAnimator.setDuration(5000);
//        valueAnimator.start();
    }

    private void setAlphaAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1000);
        animationSet.addAnimation(alphaAnimation);
        mImageView.startAnimation(animationSet);
    }

    private void setScaleAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 3f, 1, 3f,
                Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0f);
        scaleAnimation.setDuration(500);
        animationSet.addAnimation(scaleAnimation);
        mImageView.startAnimation(animationSet);
    }
}
