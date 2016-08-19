package com.meizu.contentprovider;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.meizu.appcenter.R;

import java.util.List;

/**
 * Created by root on 14-11-19.
 */
public class NoteActivity extends Activity{

    private Button mNewNoteButton;
    private Button mServiceButton;
    private Button mMessengerButton;
    private Button mBindButton;
    private ListView mNoteListView;
    private Intent mIntent;
    public  SimpleCursorAdapter mAdapter;
    public  ContentResolver contentResolver;
    private Cursor mCursor;
    private Uri selectUri;


    Messenger mService = null;
    boolean mBound;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Activity已经绑定了Service
            // 通过参数service来创建Messenger对象，这个对象可以向Service发送Message，与Service进行通信
            mService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
            mBound = false;
        }
    };


    private INote mNote;
    //private Intent mIntent;
    //private List<Note> mNotes;

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("ServiceConnection", "onServiceConnected() called");
            mNote = INote.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected() called");
        }
    };

    public void sayHello(View v) {
        if (!mBound) return;
        // 向Service发送一个Message

        Message msg = Message.obtain(null, MessengerService.MSG_HELLO, 0, 0);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mNewNoteButton = (Button)findViewById(R.id.creatNote);
        mServiceButton = (Button)findViewById(R.id.startservice);
        mMessengerButton = (Button)findViewById(R.id.messemger);
        mBindButton = (Button)findViewById(R.id.start);
        mNoteListView = (ListView)findViewById(R.id.noteList);

        mServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent();
                mIntent.setClass(NoteActivity.this, ServiceActivity.class);

                startActivity(mIntent);
            }
        });


        mBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(NoteActivity.this,NoteService.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("msg","android");
//                intent.putExtras(bundle);
                intent.putExtra("msg","android");
                startService(intent);
            }
        });

        mMessengerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayHello(v);
            }
        });

        contentResolver = getContentResolver();

//        initData();
        mAdapter = new SimpleCursorAdapter(this, R.layout.content_listitem, mCursor,
                new String[]{"_id", "calendar_displayName", "ownerAccount","duration","minutes"}, new int[]{R.id.id, R.id.title, R.id.content,R.id.duration,R.id.time});
        mNoteListView.setAdapter(mAdapter);
        mNoteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIntent = new Intent();
                mIntent.setClass(NoteActivity.this,DetailActivity.class);
                Bundle bundle=new Bundle();
                mCursor.moveToPosition(position);
                bundle.putInt("id",mCursor.getInt(mCursor.getColumnIndex("_id")));
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

        mNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent();
                mIntent.setClass(NoteActivity.this,DetailActivity.class);
                startActivity(mIntent);
            }
        });

    }

    private void initData() {
        selectUri = Uri.parse("content://com.meizu.flyme.calendar/defaultCalendar");
        contentResolver.registerContentObserver(selectUri, true, new NoteObserver(new Handler()));
        mCursor=contentResolver.query(selectUri, null, null, null, null);
        if (mCursor == null){
            Toast.makeText(NoteActivity.this,"mCursor = null",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(NoteActivity.this,"mCursor != null  "+"size="+mCursor.getCount(),Toast.LENGTH_SHORT).show();
            mAdapter = new SimpleCursorAdapter(this, R.layout.content_listitem, mCursor,
                    new String[]{"_id", "calendar_displayName", "ownerAccount","duration","minutes"}, new int[]{R.id.id, R.id.title, R.id.content,R.id.duration,R.id.time});
        }

    }

    private class NoteObserver extends ContentObserver {
        @Override
        public void onChange(boolean selfChange) {
            mCursor.requery();
            mAdapter.notifyDataSetChanged();
        }

        public NoteObserver(Handler handler) {
            super(handler);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 绑定Service
        bindService(new Intent(this, MessengerService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 解绑
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}
