package com.meizu.weatherline;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.appcenter.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15-1-23.
 */
public class WeatherViewPager extends Activity  {

    private ViewPager mViewPager;
    private LineView mLineView;
    private PagerAdapter mPagerAdapter;
    private List<String> mCityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_viewpager);

        mViewPager = (ViewPager)findViewById(R.id.weatherViewPager);
        mLineView = (LineView)findViewById(R.id.weatherLine);

//        mLineView.getBackground().setAlpha(100);
        initData();

        mPagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LayoutInflater inflater = LayoutInflater.from(WeatherViewPager.this);
                View view = inflater.inflate(R.layout.weather_viewpageritem, null);

                TextView textView = (TextView)view.findViewById(R.id.weatherCity);
                textView.setText(mCityList.get(position));

                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

        };

        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                mLineView.setCurrentPosition((long)(positionOffset*2000),position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        mCityList.add("深圳");
        mCityList.add("珠海");
        mCityList.add("广州");
    }

}
