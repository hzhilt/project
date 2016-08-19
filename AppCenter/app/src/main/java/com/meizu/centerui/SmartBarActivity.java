package com.meizu.centerui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.meizu.appcenter.R;

import Utils.SmartBarUtils;

/**
 * Created by root on 14-12-22.
 */
public class SmartBarActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.centerui_main);

        Log.i("here","Activity onCreate");

        final ActionBar bar = getActionBar();
        bar.addTab(bar.newTab().setIcon(R.drawable.centerui_home).setContentDescription("home")
                .setTabListener(new MyTabListener<HomeFragment>(this, "home", HomeFragment.class)));
        bar.addTab(bar.newTab().setIcon(R.drawable.centerui_lists).setContentDescription("list")
                .setTabListener(new MyTabListener<ListsFragment>(this, "list", ListsFragment.class)));
        bar.addTab(bar.newTab().setIcon(R.drawable.centerui_search).setContentDescription("search")
                .setTabListener(new MyTabListener<SearchFragment>(this, "search", SearchFragment.class)));
        bar.addTab(bar.newTab().setIcon(R.drawable.centerui_person).setContentDescription("my")
                .setTabListener(new MyTabListener<OwnFragment>(this, "person", OwnFragment.class)));

        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // 设置ActionBar Tab显示在底栏
        SmartBarUtils.setActionBarTabsShowAtBottom(bar, true);

    }

    public static class MyTabListener<T extends Fragment> implements ActionBar.TabListener {
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        private final Bundle mArgs;
        private Fragment mFragment;

        public MyTabListener(Activity activity, String tag, Class<T> clz) {
            this(activity, tag, clz, null);
        }

        public MyTabListener(Activity activity, String tag, Class<T> clz, Bundle args) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            mArgs = args;

            mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);

            if (mFragment != null && !mFragment.isDetached()) {
                android.app.FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                ft.detach(mFragment);
                ft.commit();
            }
        }
        @Override
        public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            if (mFragment == null) {
                mFragment = android.app.Fragment.instantiate(mActivity, mClass.getName(), mArgs);
                ft.add(android.R.id.content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }

        }
        @Override
        public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
            }
        }
        @Override
        public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("here","Activity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("here","Activity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("here","Activity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("here","Activity onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("here","Activity onStop");
    }
}
