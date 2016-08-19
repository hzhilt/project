package com.meizu.centerui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.appcenter.R;

import static android.app.ActionBar.DISPLAY_SHOW_CUSTOM;

/**
 * Created by root on 14-12-22.
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener,View.OnClickListener{

    private int mActionBarOptions;
    private ViewPager mViewPager;
    private View mCustomView;
    private ImageView mScroll1;
    private ImageView mScroll2;
    private ImageView mScroll3;
    private ImageView mScroll4;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;

    private ActionBar mActionBar;
    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i("here", "HomeF onCreateView");
        View view = inflater.inflate(R.layout.centerui_home,container,false);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        mFragmentManager = getChildFragmentManager();
        mViewPager.setAdapter(new ViewPagerAdapter(mFragmentManager));

        mViewPager.setOnPageChangeListener(this);

        //mViewPager.setOffscreenPageLimit(4);

        mViewPager.setPageMargin(8);
        mViewPager.setPageMarginDrawable(android.R.drawable.divider_horizontal_bright);

        mActionBar = getActivity().getActionBar();
        mCustomView = inflater.inflate(R.layout.centerui_toptab, null);
        mActionBar.setCustomView(mCustomView);

        mScroll1 = (ImageView) mCustomView.findViewById(R.id.scroll_1);
        mScroll2 = (ImageView) mCustomView.findViewById(R.id.scroll_2);
        mScroll3 = (ImageView) mCustomView.findViewById(R.id.scroll_3);
        mScroll4 = (ImageView) mCustomView.findViewById(R.id.scroll_4);

        text1 = (TextView) mCustomView.findViewById(R.id.tab_text_1);
        text2 = (TextView) mCustomView.findViewById(R.id.tab_text_2);
        text3 = (TextView) mCustomView.findViewById(R.id.tab_text_3);
        text4 = (TextView) mCustomView.findViewById(R.id.tab_text_4);

        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);

        return view;
    }

    private void setCurrentScroll(int selection) {
        if (mScroll1 != null && mScroll2 != null && mScroll3 != null && mScroll4 != null) {
            mScroll1.setVisibility(selection == 0 ? View.VISIBLE : View.INVISIBLE);
            mScroll2.setVisibility(selection == 1 ? View.VISIBLE : View.INVISIBLE);
            mScroll3.setVisibility(selection == 2 ? View.VISIBLE : View.INVISIBLE);
            mScroll4.setVisibility(selection == 3 ? View.VISIBLE : View.INVISIBLE);
        }

        if(text1 != null && text2 != null && text3 != null && text4 != null){

            int black = this.getResources().getColor(R.color.black);
            int blue = this.getResources().getColor(R.color.myblue);
            text1.setTextColor(selection == 0 ? blue: black);
            text2.setTextColor(selection == 1 ? blue: black);
            text3.setTextColor(selection == 2 ? blue: black);
            text4.setTextColor(selection == 3 ? blue: black);

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentScroll(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_text_1:
                mViewPager.setCurrentItem(0, false);
                break;

            case R.id.tab_text_2:
                mViewPager.setCurrentItem(1, false);
                break;

            case R.id.tab_text_3:
                mViewPager.setCurrentItem(2, false);
                break;

            case R.id.tab_text_4:
                mViewPager.setCurrentItem(3,false);
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return new MainFragment();
            else
                return new PageFragment(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("here", "HomeF onCreate");
        mActionBarOptions = mActionBar.getDisplayOptions();
        mActionBar.setDisplayOptions(DISPLAY_SHOW_CUSTOM,
                DISPLAY_SHOW_CUSTOM |mActionBarOptions);
        setCurrentScroll(mViewPager.getCurrentItem());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("here", "HomeF onCreate");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("here", "HomeF onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("here", "HomeF onStart");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("here", "HomeF onActivityCreated");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("here", "HomeF onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("here", "HomeF onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("here", "HomeF onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("here", "HomeF onDetach");
    }
}
