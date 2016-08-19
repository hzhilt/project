package com.meizu.contentprovider;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

/**
 * Created by root on 14-11-26.
 */
public class NoteService extends Service {


    private NoteDbHelper mNoteDbHelper = new NoteDbHelper(NoteService.this);
    private NoteServiceHelper mNoteServiceHelper = new NoteServiceHelper(mNoteDbHelper) ;


    INote.Stub stub = new INote.Stub() {
        @Override
        public String test(String name) throws RemoteException{
            return "hello,"+name;
        }
        @Override
        public List<Note> findAllNote() throws RemoteException{
            return mNoteServiceHelper.findAllNote();
        }

        @Override
        public void saveNote(Note note) throws RemoteException{
            mNoteServiceHelper.saveNote(note);
        }
        @Override
        public void deleteNote(int id) throws RemoteException{
            mNoteServiceHelper.deleteNote(id);
        }
        @Override
        public void updateNote(Note note) throws RemoteException{
            mNoteServiceHelper.updateNote(note);
        }
        @Override
        public Note queryNote(int id) throws RemoteException{
            return mNoteServiceHelper.query(id);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //
        //String msg = "msg";
        //String string = intent.getCharArrayExtra(msg);

        Log.i("here1","start");
        String string = (String)intent.getCharSequenceExtra("msg");
        Log.i("here2",string);

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

//        Messenger messenger = new Messenger();
//        messenger.
    }
}
