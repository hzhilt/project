package com.meizu.md;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.ActionBar.DISPLAY_SHOW_CUSTOM;
import static android.app.ActionBar.DISPLAY_SHOW_TITLE;


import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.meizu.appcenter.R;

/**
 * Created by huangzhihao on 15-11-17.
 */
public class MDActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private String[] lvs = {"List Item 01", "List Item 02", "List Item 03", "List Item 04"};
    private ArrayAdapter arrayAdapter;
    private SimpleCursorAdapter mSCAdapter;
    private ListView mListView;
    private TextView mTv;
    private Handler mHandler = new Handler();

    private ScalpelFrameLayout mScalpel;

    private static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Events._ID,                  // 0  do not remove; used in DeleteEventHelper
            CalendarContract.Events.TITLE,                // 1  do not remove; used in DeleteEventHelper
            CalendarContract.Events.RRULE,                // 2  do not remove; used in DeleteEventHelper
            CalendarContract.Events.ALL_DAY,              // 3  do not remove; used in DeleteEventHelper
            CalendarContract.Events.CALENDAR_ID,          // 4  do not remove; used in DeleteEventHelper
            CalendarContract.Events.DTSTART,              // 5  do not remove; used in DeleteEventHelper
            CalendarContract.Events._SYNC_ID,             // 6  do not remove; used in DeleteEventHelper
            CalendarContract.Events.EVENT_TIMEZONE,       // 7  do not remove; used in DeleteEventHelper
            CalendarContract.Events.DESCRIPTION,          // 8
            CalendarContract.Events.EVENT_LOCATION,       // 9
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, // 10
            CalendarContract.Events.DISPLAY_COLOR,        // 11 If SDK < 16, set to Calendars.CALENDAR_COLOR.
            CalendarContract.Events.HAS_ATTENDEE_DATA,    // 12
            CalendarContract.Events.ORGANIZER,            // 13
            CalendarContract.Events.HAS_ALARM,            // 14
            CalendarContract.Calendars.MAX_REMINDERS,     // 15
            CalendarContract.Calendars.ALLOWED_REMINDERS, // 16
            CalendarContract.Events.CUSTOM_APP_PACKAGE,   // 17
            CalendarContract.Events.CUSTOM_APP_URI,       // 18
            CalendarContract.Events.ORIGINAL_SYNC_ID,     // 19 do not remove; used in DeleteEventHelper
            CalendarContract.Events.AVAILABILITY,         // 20
            CalendarContract.Events.SYNC_DATA1,           // 21
            CalendarContract.Events.DTEND,                // 22
            CalendarContract.Events.DURATION,             // 23
    };

    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md);
        findViews(); //获取控件
        //京东RunningMan动画效果，和本次Toolbar无关
        //mAnimationDrawable = (AnimationDrawable) ivRunningMan.getBackground();
        //mAnimationDrawable.start();
        toolbar.setTitle("Toolbar");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Toast.makeText(MDActivity.this,"opened",Toast.LENGTH_SHORT).show();
                //mAnimationDrawable.stop();
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Toast.makeText(MDActivity.this,"closed",Toast.LENGTH_SHORT).show();
                //mAnimationDrawable.start();
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mListView = (ListView) findViewById(R.id.listView);
        mTv = (TextView) findViewById(R.id.answer);
        //设置菜单列表
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lvs);
        lvLeftMenu.setAdapter(arrayAdapter);

        mSCAdapter = new SimpleCursorAdapter(MDActivity.this,
                android.R.layout.simple_list_item_2, null,
                new String[] { CalendarContract.Events._ID, CalendarContract.Events.TITLE },
                new int[] { android.R.id.text1, android.R.id.text2 }, 0);

        mListView.setAdapter(mSCAdapter);

        Time time = new Time();
        time.setToNow();
        Log.i("weekday", time.weekDay + "");


//        mTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int x = 1;
                        while (true){

                            if ((x%2 == 1) && (x%3 == 1) &&(x%4 == 1) &&(x%5 == 1) &&(x%6 == 1) &&(x%7 == 0)){
                                final int finalX = x;
                                Log.d("answer",x+ "");
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTv.setText(Integer.toString(finalX));
                                    }
                                });
                                break;
                            }else {
                                x++;
                            }
                        }
                    }
                });

                t.start();
//            }
//        });


        initLoader();
    }

    private void initLoader() {

        mUri = CalendarContract.Events.CONTENT_URI;
        getSupportLoaderManager().initLoader(0,null, new LoaderManager.LoaderCallbacks<Cursor>(){

            @Override
            public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
                CursorLoader cursorLoader = new CursorLoader(MDActivity.this,mUri,EVENT_PROJECTION,null,null,null);
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                mSCAdapter.swapCursor(cursor);
            }

            @Override
            public void onLoaderReset(Loader loader) {
                mSCAdapter.swapCursor(null);
            }
        });
    }

    private void findViews() {
        mScalpel = (ScalpelFrameLayout) findViewById(R.id.scalpel);

        mScalpel.setLayerInteractionEnabled(false);

        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);

        Switch enabledSwitch = new Switch(this);
        enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (first) {
//                    first = false;
//                    Toast.makeText(MDActivity.this, R.string.first_run, LENGTH_LONG).show();
//                }

                mScalpel.setLayerInteractionEnabled(isChecked);
                invalidateOptionsMenu();
            }
        });

        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mDrawerLayout.closeDrawers();

//                MenuFragment fragment = MenuFragment.newInstance();
//
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.main_frame, fragment);
//                ft.show(fragment);
//                ft.commit();

                switch(position){
                    case 0:
                        Intent intent = new Intent();
                        intent.setClass(MDActivity.this,OkHttpActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent appIntent = new Intent();
                        appIntent.setClass(MDActivity.this,AppListActivity.class);
                        startActivity(appIntent);
                        break;
                    case 2:
                        Intent loaderIntent = new Intent();
                        loaderIntent.setClass(MDActivity.this,LoaderCustom.class);
                        startActivity(loaderIntent);
                        break;
                    case 3:
                        Intent calendarIntent = new Intent();
                        calendarIntent.setClass(MDActivity.this,CalendarDbActivity.class);
                        startActivity(calendarIntent);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mScalpel.isLayerInteractionEnabled()) {
            return false;
        }
        menu.add("Draw Views")
                .setCheckable(true)
                .setChecked(mScalpel.isDrawingViews())
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override public boolean onMenuItemClick(MenuItem item) {
                        boolean checked = !item.isChecked();
                        item.setChecked(checked);
                        mScalpel.setDrawViews(checked);
                        return true;
                    }
                });
        menu.add("Draw IDs")
                .setCheckable(true)
                .setChecked(mScalpel.isDrawingIds())
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override public boolean onMenuItemClick(MenuItem item) {
                        boolean checked = !item.isChecked();
                        item.setChecked(checked);
                        mScalpel.setDrawIds(checked);
                        return true;
                    }
                });
        return true;
    }
}
