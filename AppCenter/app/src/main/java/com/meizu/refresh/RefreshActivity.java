package com.meizu.refresh;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by huangzhihao on 15-10-18.
 */
public class RefreshActivity extends Activity {

    ListView mListView;
    SimpleAdapter mAdapter;
    List<Map<String,Integer>> mData ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mListView = (ListView)findViewById(R.id.activity_main_listview);
        getInitData();
        mAdapter = new SimpleAdapter(this,mData,android.R.layout.simple_list_item_2,new String[]{"姓名","性别"},new int[]{android.R.id.text1,android.R.id.text2});

        mListView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getInitData(){

        mData = new ArrayList<Map<String, Integer>>();
        Map<String, Integer> item;
        item = new HashMap<String, Integer>();
        item.put("姓名", 1);
        mData.add(item);

        item = new HashMap<String, Integer>();
        item.put("姓名", 2);
        mData.add(item);

        item = new HashMap<String, Integer>();
        item.put("姓名", 3);
        mData.add(item);

    }

    private void refreshData(){
        for (int i = 0;i <= 10;i++){
            Map<String, Integer> item;
            item = new HashMap<String, Integer>();
            int what = new Random().nextInt(100);
            item.put("姓名",what);
            mData.add(0,item);
        }
    }
}
