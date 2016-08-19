package com.meizu.listviewload;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.meizu.appcenter.R;
import com.meizu.gridview.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 14-11-13.
 */
public class ListViewLoad extends Activity implements AbsListView.OnScrollListener {

    private ListView mListView;
    private ColorAdapter mColorAdapter;
    private View mViewLoad;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private Handler mHandlerload;
    private Handler mHandlerRefresh;

    private View mViewLoadRefresh;
    private ProgressBar mProgressBarRefresh;
    private TextView mTextViewRefresh;

    private int mTotal;
    private final int MAX_NUM = 50;

    private int mLastVisibleIndex;
    public int EACH_LOAD_ITEM = 10;
    private static  boolean isLoading = false;

    public List<Data> mData = new ArrayList<Data>();
    private String[] COLOR = {"#ffffe0","#ffb6c1","#ff8c00","#ff4500","#ff00ff","#ff0000","#adff2f","#87cefa","#000080"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewload_main);

        mListView = (ListView)findViewById(R.id.listViewColor);

        mViewLoad = getLayoutInflater().inflate(R.layout.listviewload_moredata,null);
        mProgressBar = (ProgressBar) mViewLoad.findViewById(R.id.progressBar);
        mTextView = (TextView)mViewLoad.findViewById(R.id.textViewLoad);

        mViewLoadRefresh = getLayoutInflater().inflate(R.layout.listviewload_head,null);
        mProgressBarRefresh = (ProgressBar) mViewLoadRefresh.findViewById(R.id.progressBarHead);
        mTextViewRefresh = (TextView) mViewLoadRefresh.findViewById(R.id.textViewRefresh);

        mHandlerload = new Handler();

        createData(3 * EACH_LOAD_ITEM);

        mColorAdapter = new ColorAdapter(this,mData);

        mListView.addFooterView(mViewLoad);
        mListView.addHeaderView(mViewLoadRefresh);
        mListView.setAdapter(mColorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewLoad.this,"点击了第"+position+"行",Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnScrollListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                && mLastVisibleIndex == mColorAdapter.getCount()){
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
            mHandlerload.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading();
                    mTextView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                    mColorAdapter.notifyDataSetChanged();
                }
            }, 2000);

        }

        if(mTotal == MAX_NUM/3 +1)
        {
            mListView.removeFooterView(mViewLoad);
            Toast.makeText(this,"没有更多数据",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onScroll(AbsListView view, final int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        mLastVisibleIndex = firstVisibleItem + visibleItemCount - 2;
        mTotal = totalItemCount;
        if( view.getLastVisiblePosition() == totalItemCount && !isLoading) {
            isLoading = true;
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
            mHandlerload.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading();
                    isLoading = false;
                    mTextView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                    mColorAdapter.notifyDataSetChanged();
                }
            }, 2000);
        }

        if(totalItemCount == MAX_NUM/3 +1)
        {
            mListView.removeFooterView(mViewLoad);
            Toast.makeText(this,"没有更多数据",Toast.LENGTH_SHORT).show();
        }

    }

    private void createData(int n) {
        for(int i=0;i<n;i++)
        {
            Random random = new Random();
            Data data = new Data();
            data.mColorID = COLOR [random.nextInt(9)];
            data.mCheck = false;
            data.mStatus = false;
            mData.add(data);
        }
    }


    public void loading(){

        int count = mColorAdapter.getCount();
        if(3*count + 3*EACH_LOAD_ITEM < MAX_NUM)
        {
            createData(3 * EACH_LOAD_ITEM);
        }
        else {
            createData(MAX_NUM - 3 * count);
        }

    }
}
