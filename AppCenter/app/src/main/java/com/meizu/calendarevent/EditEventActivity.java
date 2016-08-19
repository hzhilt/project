package com.meizu.calendarevent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.meizu.appcenter.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 15-3-7.
 */
public class EditEventActivity extends Activity implements View.OnClickListener,View.OnTouchListener{

    private static String sCalanderURL = "content://com.android.calendar/calendars";
    private Calendar mBeginTime = Calendar.getInstance();
    private Calendar mEndTime = Calendar.getInstance();

    private List<Event> mEventList = new ArrayList<>();
    public  ContentResolver mContentResolver;
    private Cursor mEventCursor;

    private EventListAdapter mAdapter;
    private int UPDATE = 0;


    private EditText mContentEdit;
    private EditText mStartTimeEdit;
    private EditText mEndTimeEdit;
    private EditText mLocationEdit;
    private EditText mDescribEdit;
    private Button   mSubmitButton;
    private Button   mCancelButton;
    private Button   mDeleteButton;

    private ListView mEventListView;

    private String calID = "";
    private Long eventID;
    private int clickPosition;
    private boolean newState = true;
    private Context mContext;
    private QueryHandler mQueryHandler = new QueryHandler(this.getContentResolver());
//    Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            if(msg.what == UPDATE){
//                mEventList = (List<Event>)msg.obj;
//                mAdapter.setEvent(mEventList);
//                mAdapter.notifyDataSetChanged();
//            }
//        }
//    };

    public void onEventMainThread(List<Event> list){
        mEventList = list;
        mAdapter.setEvent(mEventList);
        mAdapter.notifyDataSetChanged();
    }

    public void onEventMainThread(String s){
        Toast.makeText(this,"Message from service :"+s,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calendarevent_main);
        mContext = getApplicationContext();
        initCom();
        findAllEvent();

        Intent intent = new Intent();
        intent.setClass(EditEventActivity.this,MyService.class);
        startService(intent);

        EventBus.getDefault().register(this);
    }

    private void findAllEvent() {

        mQueryHandler.startQuery(0,null,CalendarContract.Events.CONTENT_URI,null,null,null,"_id desc");
//
        mContentResolver = getContentResolver();
        mContentResolver.registerContentObserver(CalendarContract.Events.CONTENT_URI, true, new NoteObserver(new Handler()));
        mEventCursor = mContentResolver.query(CalendarContract.Events.CONTENT_URI, null, null, null, "_id desc");

    }

    private void initCom() {
        mContentEdit = (EditText)findViewById(R.id.contentEdit);
        mStartTimeEdit = (EditText)findViewById(R.id.startEdit);
        mStartTimeEdit.setOnTouchListener(this);
        mEndTimeEdit = (EditText)findViewById(R.id.endEdit);
        mEndTimeEdit.setOnTouchListener(this);
        mLocationEdit = (EditText)findViewById(R.id.locationEdit);
        mDescribEdit = (EditText)findViewById(R.id.descripEdit);
        mSubmitButton = (Button)findViewById(R.id.submit_event);
        mCancelButton = (Button)findViewById(R.id.cancel);
        mDeleteButton = (Button)findViewById(R.id.delete);
        mCancelButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
        mEventListView = (ListView)findViewById(R.id.caleventlist);

        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                newState = false;
                clickPosition = position;
                eventID = Long.parseLong(mEventList.get(position).id);
                mContentEdit.setText(mEventList.get(position).title);
                mStartTimeEdit.setText(mEventList.get(position).startTime);
                mEndTimeEdit.setText(mEventList.get(position).endTime);
                mLocationEdit.setText(mEventList.get(position).location);
                mDescribEdit.setText(mEventList.get(position).descripetion);

                mSubmitButton.setText("更新");

                mDeleteButton.setVisibility(View.VISIBLE);
                mDeleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri deleteUri = null;
                        deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
                        mContentResolver.delete(deleteUri, null, null);
                        mContext.getContentResolver().notifyChange(CalendarContract.Events.CONTENT_URI,null);
                        mDeleteButton.setVisibility(View.INVISIBLE);
                        cleanText();
                        mSubmitButton.setText("OK");
                    }
                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.submit_event) {

            if (newState) {
                Cursor userCursor = mContentResolver.query(Uri.parse(sCalanderURL), null, null, null, null);
                if (userCursor.getCount() > 0) {
                    userCursor.moveToLast();
                    calID = userCursor.getString(userCursor.getColumnIndex("_id"));
                } else {
                    Toast.makeText(this, "没有账户，请先添加账户", 0).show();
                    return;
                }

                long startMillis = 0;
                long endMillis = 0;
                String title = mContentEdit.getText().toString();
                String descr = mDescribEdit.getText().toString();
                String timeZone = "Asia/Shanghai";
                String location = mLocationEdit.getText().toString();

                startMillis = mBeginTime.getTimeInMillis();
                endMillis = mEndTime.getTimeInMillis();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, title);
                values.put(CalendarContract.Events.DESCRIPTION, descr);
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);
                values.put(CalendarContract.Events.EVENT_LOCATION, location);
                Uri uri = mContentResolver.insert(CalendarContract.Events.CONTENT_URI, values);
                mContext.getContentResolver().notifyChange(CalendarContract.Events.CONTENT_URI,null);
                cleanText();
            }
            else {
                ContentValues values = new ContentValues();
                Uri updateUri = null;
                long startMillis = 0;
                long endMillis = 0;
                String title = mContentEdit.getText().toString();
                String descr = mDescribEdit.getText().toString();
                String timeZone = "Asia/Shanghai";
                String location = mLocationEdit.getText().toString();

                startMillis = mBeginTime.getTimeInMillis();
                endMillis = mEndTime.getTimeInMillis();
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, title);
                values.put(CalendarContract.Events.DESCRIPTION, descr);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);
                values.put(CalendarContract.Events.EVENT_LOCATION, location);
                updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
                mContentResolver.update(updateUri, values, null, null);
                mContext.getContentResolver().notifyChange(CalendarContract.Events.CONTENT_URI,null);

                newState = true;
                cleanText();
                mSubmitButton.setText("ok");
            }
        }
        if (v.getId() == R.id.cancel){
            cleanText();
            mSubmitButton.setText("ok");
            newState = true;

        }
    }

    public void cleanText() {
        mContentEdit.setText(null);
        mStartTimeEdit.setText(null);
        mEndTimeEdit.setText(null);
        mLocationEdit.setText(null);
        mDescribEdit.setText(null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.calendar_datadialog, null);
            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
            final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
            builder.setView(view);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.MINUTE);

            if (v.getId() == R.id.startEdit) {
                final int inType = mStartTimeEdit.getInputType();
                mStartTimeEdit.setInputType(InputType.TYPE_NULL);
                mStartTimeEdit.onTouchEvent(event);
                mStartTimeEdit.setInputType(inType);
                mStartTimeEdit.setSelection(mStartTimeEdit.getText().length());

                builder.setTitle("选取起始时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append("  ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());

                        mStartTimeEdit.setText(sb);
                        mBeginTime.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());

                        mEndTimeEdit.requestFocus();

                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.endEdit) {
                int inType = mEndTimeEdit.getInputType();
                mEndTimeEdit.setInputType(InputType.TYPE_NULL);
                mEndTimeEdit.onTouchEvent(event);
                mEndTimeEdit.setInputType(inType);
                mEndTimeEdit.setSelection(mEndTimeEdit.getText().length());

                builder.setTitle("选取结束时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append("  ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());
                        mEndTimeEdit.setText(sb);
                        mEndTime.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());


                        dialog.cancel();
                    }
                });
            }

            Dialog dialog = builder.create();
            dialog.show();
        }

        return true;
    }

    private String stringToCalendar(String string){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        if(string !=null ){
            long date = Long.parseLong(string);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            return  formatter.format(calendar.getTime());
        }

        return null;
    }

    private class NoteObserver extends ContentObserver {
        @Override
        public void onChange(boolean selfChange) {

            Thread thread = new Thread(){

                List<Event> list = new ArrayList<>();

                @Override
                public void run() {
                    ContentResolver contentResolver = getContentResolver();
                    Cursor newCursor;
                    newCursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, null, null, null, "_id desc");

                    while(newCursor.moveToNext()){
                        Event event = new Event();
                        event.id = newCursor.getString(newCursor.getColumnIndex(CalendarContract.Events._ID));
                        event.title = newCursor.getString(newCursor.getColumnIndex(CalendarContract.Events.TITLE));
                        event.startTime = stringToCalendar(newCursor.getString(newCursor.getColumnIndex(CalendarContract.Events.DTSTART)));
                        event.endTime = stringToCalendar(newCursor.getString(newCursor.getColumnIndex(CalendarContract.Events.DTEND)));
                        event.descripetion = newCursor.getString(newCursor.getColumnIndex(CalendarContract.Events.DESCRIPTION));
                        event.location = newCursor.getString(newCursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
                        list.add(event);
                    }

//                    Message message = new Message();
//                    message.what = UPDATE;
//                    message.obj = list;
//                    mHandler.sendMessage(message);

                    EventBus.getDefault().post(list);
                }
            };

            thread.start();
        }

        public NoteObserver(Handler handler) {
            super(handler);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    class QueryHandler extends AsyncQueryHandler{

        public QueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            while(cursor.moveToNext()){
                Event event = new Event();
                event.id = cursor.getString(cursor.getColumnIndex(CalendarContract.Events._ID));
                event.title = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
                event.startTime = stringToCalendar(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART)));
                event.endTime = stringToCalendar(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTEND)));
                event.descripetion = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION));
                event.location = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
                mEventList.add(event);
            }
            mAdapter = new EventListAdapter(mContext,mEventList);
            mEventListView.setAdapter(mAdapter);
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {

        }

        @Override
        protected void onUpdateComplete(int token, Object cookie, int result) {
            super.onUpdateComplete(token, cookie, result);
        }

        @Override
        protected void onDeleteComplete(int token, Object cookie, int result) {
            super.onDeleteComplete(token, cookie, result);
        }
    }
}
