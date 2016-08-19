package com.meizu.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 14-11-19.
 */
public class NoteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "note.db";
    private static final int VERSION = 1;

    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE note (_id integer primary key autoincrement, title varchar(20), content varchar(100),time varchar(20));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE note ADD COLUMN time varchar(20);");
        //db.execSQL("ALTER TABLE note ALTER COLUMN createtime varchar(20) null");
    }

}
