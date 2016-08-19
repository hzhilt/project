package com.meizu.photoanimation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.meizu.appcenter.R;
import com.meizu.photos.ImageInfo;
import com.meizu.photos.ThumbAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-1-19.
 */
public class PhotoAnimationActivity extends Activity{

    private static final String PACKAGE = "PhotoAnimation";
    private GridView mGridView;
    private List<String> mUriList = new ArrayList<>();
    private Handler mMainHandler = new Handler();
    private ThumbAdapter mThumbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_main);

        mGridView = (GridView)findViewById(R.id.photos);

        ImageInfo imageInfo = new ImageInfo(this);
        mUriList = imageInfo.getUrlList();
        mThumbAdapter = new ThumbAdapter(this, mUriList,mMainHandler);
        mGridView.setAdapter(mThumbAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int[] screenLocation = new int[2];
                view.getLocationOnScreen(screenLocation);
                String url = mUriList.get(position);
                Intent subActivity = new Intent(PhotoAnimationActivity.this,
                        ImageBigActivity.class);
                int orientation = getResources().getConfiguration().orientation;
                subActivity.
                        putExtra(PACKAGE + ".orientation", orientation).
                        putExtra(PACKAGE + ".url", url).
                        putExtra(PACKAGE + ".left", screenLocation[0]).
                        putExtra(PACKAGE + ".top", screenLocation[1]).
                        putExtra(PACKAGE + ".width", view.getWidth()).
                        putExtra(PACKAGE + ".height", view.getHeight());
                startActivity(subActivity);
                overridePendingTransition(0,0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}
