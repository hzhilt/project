package com.meizu.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GridActivity extends Activity {

    private GridView mGridView;
    private MyAdapter mMyAdapter;
    public List<Data> mData = new ArrayList<Data>();
    public TextView mTextView;
    protected boolean isMulChoice = false;

    private String[] COLOR = {"#ffffe0","#ffb6c1","#ff8c00","#ff4500","#ff00ff","#ff0000","#adff2f","#87cefa","#000080"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        mGridView = (GridView)findViewById(R.id.gridView);
        mTextView = (TextView)findViewById(R.id.textView);

        creatData();

        mMyAdapter = new MyAdapter(this,mData,isMulChoice);
        mGridView.setAdapter(mMyAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int i;

                if(isMulChoice == false) {
                    Toast.makeText(GridActivity.this, "点击了第" + (position + 1) + "个", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(mData.get(position).mStatus== false) {
                        mData.get(position).mCheck = true;
                        mData.get(position).mStatus = true;
                    }
                    else{
                        mData.get(position).mCheck = false;
                        mData.get(position).mStatus = false;

                        int j=0;
                        for (int i=0;i<mData.size();i++)
                        {
                            if (mData.get(i).mStatus == true)
                                j++;
                        }
                        if(j == 0){
                            isMulChoice = false;
                            mMyAdapter.setMul(isMulChoice);
                            mMyAdapter.notifyDataSetChanged();
                        }
                    }
                    mMyAdapter.setData(mData);
                    mMyAdapter.notifyDataSetChanged();
                }

            }
        });

        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                isMulChoice = true;
                mData.get(position).mStatus = true;
                mMyAdapter.setData(mData);
                mMyAdapter.setMul(isMulChoice);
                mMyAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if(isMulChoice == true){
            isMulChoice = false;
            mMyAdapter.setMul(isMulChoice);
            mMyAdapter.notifyDataSetChanged();
        }
        else{
            finish();
            System.exit(0);
        }
    }

    private void creatData() {
        for(int i=0;i<60;i++)
        {
            Random random = new Random();
            Data data = new Data();
            data.mColorID = COLOR [random.nextInt(9)];
            data.mCheck = false;
            data.mStatus = false;
            mData.add(data);
        }
    }

}

