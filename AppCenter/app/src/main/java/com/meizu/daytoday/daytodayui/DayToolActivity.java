package com.meizu.daytoday.daytodayui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import android.view.View.OnClickListener;
import android.widget.TextView;

import com.meizu.appcenter.R;

import java.util.Random;

/**
 * Created by huangzhihao on 15-6-8.
 */
public class DayToolActivity extends FragmentActivity {

    private static final String TAG = "DayToolActivity";

    private TabHost mTabHost;

    private ViewPager mViewPager;

    private PagerAdapter mPagerAdapter;

    private int[] addresses = { 1, 2, 3 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_daytoday);
        mViewPager = (ViewPager) findViewById(R.id.viewPager1);
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("one").setIndicator("one-1")
                .setContent(R.id.viewPager1));
        mTabHost.addTab(mTabHost.newTabSpec("two").setIndicator("two-2")
                .setContent(R.id.viewPager1));
        mTabHost.addTab(mTabHost.newTabSpec("three").setIndicator("three-3")
                .setContent(R.id.viewPager1));
        TabWidget tabWidget = mTabHost.getTabWidget();
        
        mViewPager.setCurrentItem(1);
        mTabHost.setCurrentTab(1);
        int count = tabWidget.getChildCount();
        for (int i = 0; i != count; i++)
        {
            final int index = i;
            tabWidget.getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mTabHost.setCurrentTab(index);
                    mViewPager.setCurrentItem(index);
                }
            });
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId)
            {
                Log.i(TAG, "@--> onTabChanged by tabId: " + tabId);
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0)
            {
                Log.i(TAG, "@--> onPageSelected: " + arg0);
                mTabHost.setCurrentTab(arg0);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
            }
            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });

    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0:

                    return new DayNumFragment();

                case 1:

                    return new OffsetFragment();

                case 2:

                    return new OldToNewFragment();

                default:
                    break;
            }
            return MyFragment.create(addresses[position]);
        }
        @Override
        public int getCount()
        {
            return addresses.length;
        }
    }


    public static class MyFragment extends Fragment
    {
        public static MyFragment create(int address)
        {
            Log.i(TAG, "@--> MyFragment.create()");
            MyFragment f = new MyFragment();
            Bundle b = new Bundle();
            b.putInt("address", address);
            //b.putString("address", address);
            f.setArguments(b);
            return f;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            Random r = new Random(System.currentTimeMillis());
            Bundle b = getArguments();

            int num = b.getInt("address");
            View v = null;
            switch (num){
                case 1:
                    v = inflater.inflate(R.layout.fragment_daytoday_viewpager, null);
                    break;
                case 2:
                    v = inflater.inflate(R.layout.fragment_daytoday_viewpager, null);
                    break;

                case 3:
                    v = inflater.inflate(R.layout.fragment_daytoday_viewpager, null);
                    break;
            }

            return v;
        }
    }
}
