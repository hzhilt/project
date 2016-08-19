package com.meizu.customview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14-12-25.
 */
public class CustomListActivity extends Activity {

    private MyListView myListView;

    private MyAdapter mAdapter;

    private List<String> mContentList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customview_main);
        initList();
        myListView = (MyListView) findViewById(R.id.my_list_view);
        myListView.setOnDeleteListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(int index) {
                mContentList.remove(index);
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter = new MyAdapter(this, 0, mContentList);
        myListView.setAdapter(mAdapter);
    }

    private void initList() {
        for (int i = 1;i<21;i++){
            mContentList.add("Content Item "+i);
        }
    }
}
