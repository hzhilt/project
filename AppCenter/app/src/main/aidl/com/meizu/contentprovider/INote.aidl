package com.meizu.contentprovider;

import com.meizu.contentprovider.Note;

interface INote {
    List<Note> findAllNote();
    void saveNote(in Note note);
    void deleteNote(int id);
    void updateNote(in Note note);
    Note queryNote(int id);
    String test(String name);
}