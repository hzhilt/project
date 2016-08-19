package com.meizu.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by root on 14-11-19.
 */
public class NoteProvider extends ContentProvider {

    private NoteDbHelper mNoteDbHelper;

    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int NOTES = 1;
    private static final int NOTE = 2;

    static {
        mUriMatcher.addURI("com.meizu.contentprovider","note",NOTES);
        mUriMatcher.addURI("com.meizu.contentprovider","note/#",NOTE);
    }

    @Override
    public boolean onCreate() {
        this.mNoteDbHelper = new NoteDbHelper(this.getContext());
        return mNoteDbHelper != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mNoteDbHelper.getReadableDatabase();
        if (mUriMatcher.match(uri) == NOTES) {
            return db.query("note",projection,selection,selectionArgs,null,null,sortOrder);
        }else {
            long id = ContentUris.parseId(uri);
            String where = "_id=" + id;
            if (selection != null && !"".equals(selection)) {
                where = selection + " and " + where;
            }
            return db.query("note", projection, where, selectionArgs, null,
                    null, sortOrder);
        }

    }

    @Override
    public String getType(Uri uri) {
        if (mUriMatcher.match(uri) == NOTES)
        {
            return "vnd.android.cursor.dir/note";
        }else {
            return "vnd.android.cursor.item/note";
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();
        if (mUriMatcher.match(uri) == NOTES)
        {
            long rowid = db.insert("note","title",values);
            Uri insertUri = ContentUris.withAppendedId(uri,rowid);
            this.getContext().getContentResolver().notifyChange(uri,null);
            return insertUri;
        }else{
            throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();
        int count = 0;
        if (mUriMatcher.match(uri) == NOTES)
        {
            count = db.delete("note", selection, selectionArgs);
            return count;
        }else{
            long id = ContentUris.parseId(uri);
            String where = "_id="+id;
            if (!TextUtils.isEmpty(selection)){
                where = selection +" and "+ where;
            }
            count = db.delete("note",where,selectionArgs);
            this.getContext().getContentResolver().notifyChange(uri,null);
            return  count;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();
        int count = 0;
        if (mUriMatcher.match(uri) == NOTES)
        {
            count = db.update("note", values, selection, selectionArgs);
            return count;
        }else{
            long id = ContentUris.parseId(uri);
            String where = "_id="+id;
            if (!TextUtils.isEmpty(selection)){
                where = selection +" and "+ where;
            }
            count = db.update("note",values,where,selectionArgs);
            this.getContext().getContentResolver().notifyChange(uri,null);
            return  count;
        }
    }
}
