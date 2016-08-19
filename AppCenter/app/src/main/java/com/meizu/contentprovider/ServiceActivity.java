package com.meizu.contentprovider;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.meizu.appcenter.R;

import java.util.List;

/**
 * Created by root on 14-11-26.
 */
public class ServiceActivity extends Activity {

    private INote mNote;
    private Intent mIntent;
    private List<Note> mNotes;

    private Button mNewNoteButton;
    private Button mServiceButton;
    private ListView mNoteListView;
    private BaseAdapter myAdapter;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mNewNoteButton = (Button)findViewById(R.id.creatNote);
        mServiceButton = (Button)findViewById(R.id.startservice);
        mNoteListView = (ListView)findViewById(R.id.noteList);

        Intent intent = new Intent("android.intent.action.NoteService");
        bindService(intent, mConn, Context.BIND_AUTO_CREATE);

        mNewNoteButton.setEnabled(false);

        mServiceButton.setText("loading");
        mServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        mNewNoteButton.setEnabled(true);
                        mNotes = mNote.findAllNote();
                        String retVal = mNote.test("android");
                        Toast.makeText(ServiceActivity.this, retVal, Toast.LENGTH_SHORT).show();
                        mNoteListView.setAdapter(myAdapter);
                    } catch (RemoteException e) {
                        Toast.makeText(ServiceActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
            }
        });


        myAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return mNotes.size();
            }

            @Override
            public Object getItem(int position) {
                return mNotes.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(ServiceActivity.this).inflate(R.layout.content_listitem,null);
                TextView id = (TextView)convertView.findViewById(R.id.id);
                TextView title = (TextView)convertView.findViewById(R.id.title);
                TextView content = (TextView)convertView.findViewById(R.id.content);

                id.setText(Integer.toString(mNotes.get(position)._id));
                title.setText(mNotes.get(position).title);
                content.setText(mNotes.get(position).content);
                return convertView;
            }
        };

        mNoteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mIntent = new Intent();
                mIntent.setClass(ServiceActivity.this,ServiceDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("id",mNotes.get(position)._id);
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

        mNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent();
                mIntent.setClass(ServiceActivity.this,ServiceDetailActivity.class);
                startActivity(mIntent);
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            mNotes = mNote.findAllNote();
            mNoteListView.setAdapter(myAdapter);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
