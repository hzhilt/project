package com.meizu.flyme.calendar.subcription_new.show;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.ViewStub;

import com.meizu.flyme.calendar.R;
import com.meizu.flyme.calendar.RxAppCompatActivity;

/**
 * Created by huangzhihao on 16-8-16.
 */
public class ShowActivity extends RxAppCompatActivity{

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        String[] types = {"综合","大陆","TVB","韩剧","美剧","日剧"};
        initLoad(types);
    }

    @Override
    protected void setupActionBar(ActionBar actionBar) {
        super.setupActionBar(actionBar);

        mActionBar = actionBar;

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        getSupportActionBar().setTitle("电视剧");
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

    private void initLoad(String[] tools) {

        ViewStub viewPagerStub = (ViewStub) findViewById(R.id.data_viewPagerStub);
        viewPagerStub.inflate();

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ShowAdapter adapter = new ShowAdapter(getSupportFragmentManager(), tools);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }
        };

        int N = adapter.getCount();
        for (int i = 0; i < N; i++) {
            mActionBar.addTab(mActionBar.newTab().setText(adapter.getPageTitle(i)).setTabListener(tabListener));
        }

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                mActionBar.setTabScrolled(i, v, i1);
            }

            @Override
            public void onPageSelected(int i) {
                mActionBar.selectTab(mActionBar.getTabAt(i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Just turn back.
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
