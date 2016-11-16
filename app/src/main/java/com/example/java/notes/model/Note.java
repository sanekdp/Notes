package com.example.java.notes.model;

import android.database.Cursor;

import com.example.java.notes.db.NotesContract;

/**
 * Created by java on 07.11.2016.
 */

public class Note {

    private String mTitle = null;
    private String mText = null;
    private String mTime = null;

    public Note(){}

    public Note(Cursor data) {
        mTitle = data.getString(data.getColumnIndex(NotesContract.TITLE_COLUMN));
        mText = data.getString(data.getColumnIndex(NotesContract.TEXT_COLUMN));
        mTime = data.getString(data.getColumnIndex(NotesContract.TIME_COLUMN));
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }
}
