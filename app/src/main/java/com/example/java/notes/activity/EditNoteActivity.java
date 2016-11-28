package com.example.java.notes.activity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.java.notes.R;
import com.example.java.notes.db.NotesContract;
import com.example.java.notes.model.Note;
import com.example.java.notes.util.DateUtil;
import com.tjeannin.provigen.ProviGenBaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private long mId = -1;
    private String mOriginalTitle = "";
    private String mOriginalText = "";

    @BindView(R.id.title_note_text)
    protected EditText mTitleEditText;
    @BindView(R.id.content_note_text)
    protected EditText mContentEditText;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar = null;


    @NonNull
    public static Intent newInstance(@NonNull Context context) {
        return new Intent(context, EditNoteActivity.class);
    }

    @NonNull
    public static Intent newInstance(@NonNull Context context, long id) {
        Intent intent = newInstance(context);
        intent.putExtra(ProviGenBaseContract._ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);
        checkIntentByExtraId();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.note_edit);
    }

    private void checkIntentByExtraId() {
        Intent intent = getIntent();
        if(!intent.hasExtra(ProviGenBaseContract._ID)) finish();
        mId = intent.getLongExtra(ProviGenBaseContract._ID, mId);
        if(mId == -1) finish();
        getLoaderManager().initLoader(R.id.edit_note_loader, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                safetyFinish(() -> EditNoteActivity.this.finish());
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.saveBtn)
    public void onSaveBtnClick() {
        save();
        finish();
    }

    private void save() {
        updateNote();
    }

    private void updateNote() {
        ContentValues values = new ContentValues();
        values.put(NotesContract.TITLE_COLUMN, mTitleEditText.getText().toString());
        values.put(NotesContract.TEXT_COLUMN, mContentEditText.getText().toString());
        values.put(NotesContract.TIME_COLUMN, DateUtil.formatCurrentDate());
        getContentResolver().update(
                Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(mId)),
                values,
                null,
                null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                this,
                Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(mId)),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) return;
        Note note = new Note(cursor);
        mTitleEditText.setText(note.getTitle());
        mContentEditText.setText(note.getText());
        mOriginalTitle = note.getTitle();
        mOriginalText = note.getText();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onBackPressed() {
        safetyFinish(() -> EditNoteActivity.super.onBackPressed());
    }

    private void safetyFinish(Runnable finish) {
        if(mOriginalTitle.equals(mTitleEditText.getText().toString())
                && mOriginalText.equals(mContentEditText.getText().toString())) {
            finish.run();
            return;
        }
        showDoYouSureAlert(finish);
    }

    private void showDoYouSureAlert(final Runnable finish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.do_you_sure_alert_title);
        builder.setMessage(R.string.do_yout_sure_alert_do_you_want_to_save_change);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
            EditNoteActivity.this.save();
            finish.run();
        });
        builder.setNeutralButton(android.R.string.search_go, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setNegativeButton(android.R.string.no, (dialogInterface, i) -> finish.run());
        builder.show();
    }
}