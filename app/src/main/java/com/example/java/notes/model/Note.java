package com.example.java.notes.model;

/**
 * Created by java on 07.11.2016.
 */

public class Note {

    private String mTitle = null;
    private String mText = null;
    private long mTime = 0;

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

    public long getTime() {
        return mTime;
    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }
}
