package com.meizu.photos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14-12-15.
 */
public class PhotoListActivity extends Activity {

    private GridView mGridView;
    private List<String> mUriList = new ArrayList<>();
    private Handler mMainHandler = new Handler();
    private ThumbAdapter mThumbAdapter;

    private LayoutInflater mLF;
    private ProgressDialog mPgBar;
    private Handler mHandler;

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_main);

        mGridView = (GridView)findViewById(R.id.photos);

        ImageInfo imageInfo = new ImageInfo(this);
        mUriList = imageInfo.getUrlList();
        mThumbAdapter = new ThumbAdapter(this, mUriList,mMainHandler);
        mGridView.setAdapter(mThumbAdapter);

        Runtime.getRuntime().availableProcessors();

        //case 1: use loop
//        mLF = LayoutInflater.from(this);
//        mPgBar = ProgressDialog.show(this, "Please Wait...", "Please Wait...");
//        mHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                if(msg.what == 1){
//                    mPgBar.dismiss();
//                    mUriList = (List<String>)msg.obj;
//                    mThumbAdapter = new ThumbAdapter(getApplicationContext(), mUriList,mMainHandler);
//
//                    mGridView.setAdapter(mThumbAdapter);
//
//                }
//            }
//        };
//        ImageInfo imageInfo = new ImageInfo(this,mHandler);
//        imageInfo.getUrl();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("url", mUriList.get(position));
                intent.setClass(PhotoListActivity.this,ImageDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
