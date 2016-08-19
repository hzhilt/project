package com.meizu.contentprovider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.meizu.appcenter.R;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by root on 14-11-19.
 */
public class DetailActivity extends Activity {

    private EditText mTitleEdit;
    private EditText mContentEdit;
    private Button mDeleteButton;
    private Button mSaveButton;
    private Button mUpdateButton;
    private int mId;
    private Bundle mBundle;
    private ContentResolver mContentResolver;
    private Uri mInsertUri;
    private Uri mResultUri;
    public  Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);

        mTitleEdit = (EditText)findViewById(R.id.noteTile);
        mContentEdit = (EditText)findViewById(R.id.noteContent);
        mDeleteButton = (Button)findViewById(R.id.noteDelete);
        mSaveButton = (Button)findViewById(R.id.noteSave);
        mUpdateButton = (Button)findViewById(R.id.noteUpdate);
        mContentResolver = getContentResolver();
        mInsertUri = Uri.parse("content://com.meizu.contentprovider/note");

        if(null != getIntent().getExtras()){
            mSaveButton.setVisibility(View.INVISIBLE);
            mBundle = getIntent().getExtras();
            mId = mBundle.getInt("id");

            mResultUri = ContentUris.withAppendedId(mInsertUri, mId);
            cursor = mContentResolver.query(mResultUri, null, null, null, null);

            if (cursor.moveToNext())
            {
                mTitleEdit.setText(cursor.getString(cursor.getColumnIndex("title")));
                mContentEdit.setText(cursor.getString(cursor.getColumnIndex("content")));
            }
            cursor.close();
        }
        else {
            mUpdateButton.setVisibility(View.INVISIBLE);
        }

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContentResolver.delete(mResultUri,null,null);
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
                values.put("time",time);
                mContentResolver.update(mResultUri,values,null,null);

                finish();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEdit.getText().toString();
                String content = mContentEdit.getText().toString();

                if(TextUtils.isEmpty(title ) || TextUtils.isEmpty(content)) {
                    Toast.makeText(DetailActivity.this, "有信息没填写", Toast.LENGTH_SHORT).show();
                }
                else {
                    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String time = formatter.format(curDate);
                    ContentValues values = new ContentValues();
                    values.put("title", title);
                    values.put("content", content);
                    values.put("time",time);
                    mContentResolver.insert(mInsertUri, values);

                    Toast.makeText(DetailActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
}
