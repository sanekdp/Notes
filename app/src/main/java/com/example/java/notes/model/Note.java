package com.example.java.notes.model;

import android.database.Cursor;

import com.example.java.notes.db.NotesContract;
import com.tjeannin.provigen.ProviGenBaseContract;

public class Note {

    private String mTitle = null;
    private String mText = null;
    private String mTime = null;
    private long mId = -1;

    public Note(){}

    public Note(Cursor data) {
        mTitle = data.getString(data.getColumnIndex(NotesContract.TITLE_COLUMN));
        mText = data.getString(data.getColumnIndex(NotesContract.TEXT_COLUMN));
        mTime = data.getString(data.getColumnIndex(NotesContract.TIME_COLUMN));
        mId = data.getLong(data.getColumnIndex(ProviGenBaseContract._ID));
    }

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }

    public String getTime() {
        return mTime;
    }

    public long getId() {
        return mId;
    }
}
