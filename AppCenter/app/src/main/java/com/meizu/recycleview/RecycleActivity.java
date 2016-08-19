package com.meizu.recycleview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 15-1-4.
 */
public class RecycleActivity extends Activity {

    private CustomRecycleView mRecyclerView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_main);
        // 创建数据集
//        String[] dataset = new String[100];
//        for (int i = 0; i < dataset.length; i++){
//            dataset[i] = "item" + i;
//        }

        final List<Integer> dataSet;
        dataSet = new ArrayList<>(Arrays.asList(R.drawable.abc_ic_ab_back_mtrl_am_alpha,
                R.drawable.abc_ic_ab_back_mtrl_am_alpha,
                R.drawable.abc_list_pressed_holo_light,
                R.drawable.ic_tab_unselected_contacts,
                R.drawable.ic_sb_rank_on));


        mImageView = (ImageView)findViewById(R.id.id_content);
        mRecyclerView = (CustomRecycleView) findViewById(R.id.id_recyclerview_horizontal);
        SimpleAdapter simpleAdapter = new SimpleAdapter(dataSet, this);
        simpleAdapter.setOnItemClickLitener(new SimpleAdapter.OnItemClickLitener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Toast.makeText(RecycleActivity.this, position + "", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        mRecyclerView.setAdapter(simpleAdapter);

        mRecyclerView.setOnItemScrollChangeListener(new CustomRecycleView.OnItemScrollChangeListener() {
            @Override
            public void onChange(View view, int position) {
                mImageView.setImageResource(dataSet.get(position));
            }
        });
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

//        grid
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);

        //stagger
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,1);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new Decoration(this));
    }
}
