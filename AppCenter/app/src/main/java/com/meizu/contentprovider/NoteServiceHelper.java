package com.meizu.contentprovider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14-11-19.
 */
public class NoteServiceHelper {

    private NoteDbHelper mNoteDbHelper;
    public NoteServiceHelper(NoteDbHelper mNoteDbHelper) {
        this.mNoteDbHelper = mNoteDbHelper;
    }

    public void saveNote(Note note){
        SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO note(title,content,time) VALUES (?,?,?);",new Object[]{note.title,note.content,note.time});
    }

    public void deleteNote(int id){
        SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM note WHERE _id = ?",new Object[]{id});
    }

    public Note query(int id){
        SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();
        Note note = new Note();
        String string = Integer.toString(id);
        Cursor cursor = db.rawQuery("SELECT * FROM note where _id = ?",new String[]{string});

        while(cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            note.title = title;
            note.content = content;
        }
        cursor.close();
        return note;
    }

    public void updateNote(Note note){
        SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();
        db.execSQL("UPDATE note SET title = ?,content = ? ,time = ? where _id = ?",new Object[]{note.title,note.content,note.time,note._id});
    }

    public List<Note> findAllNote(){

        SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();
        List<Note> notes = new ArrayList<Note>();
        Cursor cursor=db.rawQuery("SELECT * FROM note", null);

        while (cursor.moveToNext()){
            Note note = new Note();
            int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String time = cursor.getString(cursor.getColumnIndex("time"));

            note._id = id;
            note.title = title;
            note.content = content;
            note.time = time;

            notes.add(note);
        }

        cursor.close();
        return notes;
    }


    public List<String> findAllTitle(){
        SQLiteDatabase db = mNoteDbHelper.getWritableDatabase();
        List<String> titles = new ArrayList<String>();
        Cursor cursor=db.rawQuery("SELECT * FROM note", null);

        while (cursor.moveToNext()){

            String title = cursor.getString(cursor.getColumnIndex("title"));

            titles.add(title);
        }
        return titles;
    }
}
