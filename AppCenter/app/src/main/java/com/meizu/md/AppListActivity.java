package com.meizu.md;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.meizu.appcenter.R;


/**
 * Created by huangzhihao on 15-11-30.
 */
public class AppListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist_content);

        FragmentManager fm = getFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(R.id.applist_content) == null) {
            AppListFragment list = new AppListFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.applist_content,list);
            ft.commit();
        }
    }
}
