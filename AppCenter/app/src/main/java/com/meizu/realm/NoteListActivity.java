package com.meizu.realm;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.meizu.appcenter.R;
import com.meizu.contentprovider.DetailActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by huangzhihao on 16-2-19.
 */
public class NoteListActivity extends Activity {
    private Button mNewNoteButton;
    private ListView mNoteListView;
    private Intent mIntent;

    private Realm realm;

    ContentResolver cr ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mNewNoteButton = (Button) findViewById(R.id.creatNote);
        mNoteListView = (ListView) findViewById(R.id.noteList);


        // change 1.0.0
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfig);

// Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();

        //realm = Realm.getInstance(realmConfig);

        RealmResults<Note> notes = realm.where(Note.class).findAll();
        NoteAdapter adapter = new NoteAdapter(this,notes);
        mNoteListView.setAdapter(adapter);

        mNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent();
                mIntent.setClass(NoteListActivity.this, CreateNoteActivity.class);
                startActivity(mIntent);
            }
        });

        cr = getContentResolver();


        new Thread() {
            @Override
            public void run() {
                super.run();

                Cursor cursor = cr.query(CalendarContract.Events.CONTENT_URI,null,null,null,null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {

                        cursor.moveToNext();
                    }
                }


            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
