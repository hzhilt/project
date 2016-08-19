package com.meizu.photos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.meizu.appcenter.R;
import com.meizu.loadimage.LoadImage;

/**
 * Created by root on 14-12-15.
 */
public class ImageDetailActivity extends Activity {

    private ImageView mImageView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_detail);
        mImageView = (ImageView)findViewById(R.id.imageDetail);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        mBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        mBitmap = BitmapFactory.decodeFile(url, options);
        options.inJustDecodeBounds = false;

        int h = options.outHeight;
        int w = options.outWidth;

        LoadImage loadImage = LoadImage.getInstance();
        mBitmap = loadImage.getTumb(url,w,h);
        mImageView.setImageBitmap(mBitmap);

        //mBitmap.isRecycled();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBitmap.recycle();
    }
}
