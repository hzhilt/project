package com.meizu.realm;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.meizu.appcenter.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

import io.realm.Realm;


/**
 * Created by huangzhihao on 16-2-19.
 */
public class CreateNoteActivity extends Activity {

    private EditText mTitleEdit;
    private EditText mContentEdit;
    private Button mDeleteButton;
    private Button mSaveButton;
    private Button mUpdateButton;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

        mTitleEdit = (EditText)findViewById(R.id.noteTile);
        mContentEdit = (EditText)findViewById(R.id.noteContent);
        mDeleteButton = (Button)findViewById(R.id.noteDelete);
        mSaveButton = (Button)findViewById(R.id.noteSave);
        mUpdateButton = (Button)findViewById(R.id.noteUpdate);

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContentResolver.delete(mResultUri,null,null);
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
                ContentValues values = new ContentValues();
                values.put("title", title);
                values.put("content", content);
                values.put("time", time);
//                mContentResolver.update(mResultUri,values,null,null);

                finish();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEdit.getText().toString();
                String content = mContentEdit.getText().toString();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                    Toast.makeText(CreateNoteActivity.this, "有信息没填写", Toast.LENGTH_SHORT).show();
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String time = formatter.format(curDate);

                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();

                    Note note = realm.createObject(Note.class);
                    note.setTitle(title);
                    note.setContent(content);
                    note.setCreateTime(time);


                    realm.copyToRealm(note);

                    realm.commitTransaction();

                    Toast.makeText(CreateNoteActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
