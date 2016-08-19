package com.meizu.contentprovider;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.meizu.contentprovider.INote;
import com.meizu.appcenter.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by root on 14-11-27.
 */
public class ServiceDetailActivity extends Activity {

    private EditText mTitleEdit;
    private EditText mContentEdit;
    private Button mDeleteButton;
    private Button mSaveButton;
    private Button mUpdateButton;
    private Button mOpenService;

    private int mId;
    private Bundle mBundle;

    private INote mINote;
    private Note mNote;

    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("ServiceConnection", "onServiceConnected() called");
            mINote = INote.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected() called");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

        Intent intent = new Intent("android.intent.action.NoteService");
        bindService(intent, mConn, Context.BIND_AUTO_CREATE);

        mTitleEdit = (EditText)findViewById(R.id.noteTile);
        mContentEdit = (EditText)findViewById(R.id.noteContent);
        mDeleteButton = (Button)findViewById(R.id.noteDelete);
        mSaveButton = (Button)findViewById(R.id.noteSave);
        mUpdateButton = (Button)findViewById(R.id.noteUpdate);

        mOpenService = (Button)findViewById(R.id.openservice);
        mOpenService.setVisibility(View.VISIBLE);

        if(null != getIntent().getExtras()){
            mSaveButton.setVisibility(View.INVISIBLE);
            mBundle = getIntent().getExtras();
            mId = mBundle.getInt("id");

            mDeleteButton.setEnabled(false);
            mUpdateButton.setEnabled(false);
        }
        else {
            mUpdateButton.setVisibility(View.INVISIBLE);
            mOpenService.setVisibility(View.INVISIBLE);
        }

        mOpenService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mOpenService.setVisibility(View.INVISIBLE);
                    mDeleteButton.setEnabled(true);
                    mUpdateButton.setEnabled(true);
                    mNote = mINote.queryNote(mId);
                    mTitleEdit.setText(mNote.title);
                    mContentEdit.setText(mNote.content);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mINote.deleteNote(mId);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEdit.getText().toString();
                String content = mContentEdit.getText().toString();

                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                    Toast.makeText(ServiceDetailActivity.this, "有信息没填写", Toast.LENGTH_SHORT).show();
                }
                else {
                    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String time = formatter.format(curDate);

                    Note note =new Note();
                    note.title = title;
                    note.content = content;
                    note.time = time;

                    try {
                        mINote.saveNote(note);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(ServiceDetailActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEdit.getText().toString();
                String content = mContentEdit.getText().toString();

                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String time = formatter.format(curDate);

                Note note =new Note();
                note.title = title;
                note.content = content;
                note.time = time;
                note._id = mId;

                try {
                    mINote.updateNote(note);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });



    }
}
