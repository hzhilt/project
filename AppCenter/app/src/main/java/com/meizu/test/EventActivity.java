package com.meizu.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.appcenter.R;

import org.w3c.dom.Text;

/**
 * Created by root on 14-12-29.
 */
public class EventActivity extends Activity {

    private Button mButton;
    private ImageView mFirstImageView;
    private ImageView mSecondImageView;
    private TextView mTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);
        mFirstImageView = (ImageView)findViewById(R.id.firstImage);
        mSecondImageView = (ImageView)findViewById(R.id.secondImage);
        mButton = (Button)findViewById(R.id.copyImage);
        mTextView = (TextView)findViewById(R.id.myText);
        mFirstImageView.setBackgroundResource(R.drawable.gintama);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//                view.setDrawingCacheEnabled(true);
//                view.buildDrawingCache();
//                Bitmap bitmap = view.getDrawingCache();
//                //view.setDrawingCacheEnabled(false);
                //mFirstImageView.setBackgroundResource(R.drawable.gintama);
//                final ImageView imageView = new ImageView(EventActivity.this);
//                imageView.setBackgroundResource(R.drawable.gintama);

                //mTextView.setText("hello world");

                mButton.setText("hello world!");
                ViewTreeObserver viewTreeObserver = mButton.getViewTreeObserver();

                viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        Bitmap screenshot;
                        screenshot = Bitmap.createBitmap(mButton.getWidth(), mButton.getHeight(), Bitmap.Config.ARGB_4444);
                        Canvas c = new Canvas(screenshot);
                        mButton.draw(c);

                        mSecondImageView.setImageBitmap(screenshot);
                        return true;
                    }
                });
//                if (view == null) {
//                    return;
//                }
//                Bitmap screenshot;
//                screenshot = Bitmap.createBitmap(mFirstImageView.getMeasuredWidth(), mFirstImageView.getMeasuredHeight(), Bitmap.Config.ARGB_4444);
//                Canvas c = new Canvas(screenshot);
//                mFirstImageView.draw(c);
//
//                mSecondImageView.setImageBitmap(screenshot);
            }
        });
    }

}
