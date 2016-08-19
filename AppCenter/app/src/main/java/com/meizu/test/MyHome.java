package com.meizu.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-1-9.
 */
public class MyHome extends Activity {

    private GridView mAppGrid;
    private List<ResolveInfo> mApp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_myhome_main);

        mAppGrid = (GridView)findViewById(R.id.appGrid);

        loadApp();
        mAppGrid.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mApp.size();
            }

            @Override
            public Object getItem(int position) {
                return mApp.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView i;

                if (convertView == null) {
                    i = new ImageView(MyHome.this);
                    i.setScaleType(ImageView.ScaleType.FIT_XY);
                    i.setLayoutParams(new GridView.LayoutParams(100, 100));
                } else {
                    i = (ImageView) convertView;
                }

                ResolveInfo info = mApp.get(position);
                i.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));

                return i;
            }
        });

        mAppGrid.setOnItemClickListener(listener);


    }

    private void loadApp(){
        Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mApp = getPackageManager().queryIntentActivities(mainIntent,0);
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            ResolveInfo info = mApp.get(position);

            //该应用的包名
            String pkg = info.activityInfo.packageName;
            //应用的主activity类
            String cls = info.activityInfo.name;

            ComponentName component = new ComponentName(pkg, cls);

            Intent i = new Intent();
            i.setComponent(component);
            startActivity(i);
        }

    };
}
