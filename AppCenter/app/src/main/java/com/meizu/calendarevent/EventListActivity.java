package com.meizu.calendarevent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.meizu.appcenter.R;

/**
 * Created by root on 15-3-7.
 */
public class EventListActivity extends Activity{

    ListView mEventListView;
    EventListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_eventlist);
        mEventListView = (ListView)findViewById(R.id.eventlist);

        //mAdapter = new EventListAdapter(this);

        //mEventListView.setAdapter(mAdapter);
    }
}
