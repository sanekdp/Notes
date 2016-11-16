package com.example.java.notes.providers;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.java.notes.db.NotesContract;
import com.tjeannin.provigen.ProviGenOpenHelper;
import com.tjeannin.provigen.ProviGenProvider;

/**
 * Created by java on 16.11.2016.
 */

public class NotesContentProvider extends ProviGenProvider {

    private static final Class[] CONTRACTS = new Class[]{ NotesContract.class };

    public static final String DB_NAME = "notes_database";

    public static final int DB_VERSION = 1;

    @Override
    public SQLiteOpenHelper openHelper(Context context) {
        return new ProviGenOpenHelper(getContext(), DB_NAME, null, DB_VERSION, CONTRACTS);
    }

    @Override
    public Class[] contractClasses() {
        return CONTRACTS;
    }

}
