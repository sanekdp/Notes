package com.example.java.notes.db;

import android.net.Uri;

import com.example.java.notes.BuildConfig;
import com.tjeannin.provigen.ProviGenBaseContract;
import com.tjeannin.provigen.annotation.Column;
import com.tjeannin.provigen.annotation.ContentUri;

/**
 * Created by java on 16.11.2016.
 */

public interface NotesContract extends ProviGenBaseContract {

    @Column(Column.Type.TEXT)
    public static final String TITLE_COLUMN = "TITLE";

    @Column(Column.Type.TEXT)
    public static final String TEXT_COLUMN = "TEXT";

    @Column(Column.Type.TEXT)
    public static final String TIME_COLUMN = "TIME";

    public static final String TABLE_NAME = "notes_table";

    public static final String TABLE_URI_TEMPLATE = "content://%s/%s";

    public static final String URI = String.format(
            TABLE_URI_TEMPLATE,
            BuildConfig.APPLICATION_ID,
            TABLE_NAME);

    @ContentUri
    public static final Uri CONTENT_URI = Uri.parse(URI);

}
