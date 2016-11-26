package com.example.java.notes.activity;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
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

public class AddNoteActivity extends AppCompatActivity{

    @BindView(R.id.title_note_text)
    protected EditText mTitleEditText;
    @BindView(R.id.content_note_text)
    protected EditText mContentEditText;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar = null;


    @NonNull
    public static Intent newInstance(@NonNull Context context) {
        return new Intent(context, AddNoteActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.note_create);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                safetyFinish(() -> finish());
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.saveBtn)
    public void onSaveBtnClick() {
        save();
        setResult(RESULT_OK);
        finish();
    }

    private void save() {
        insertNote();
    }

    private void insertNote() {
        ContentValues values = new ContentValues();
        values.put(NotesContract.TITLE_COLUMN, mTitleEditText.getText().toString());
        values.put(NotesContract.TEXT_COLUMN, mContentEditText.getText().toString());
        values.put(NotesContract.TIME_COLUMN, DateUtil.formatCurrentDate());
        getContentResolver().insert(NotesContract.CONTENT_URI, values);
    }

    @Override
    public void onBackPressed() {
        safetyFinish(() -> AddNoteActivity.super.onBackPressed());
    }

    private void safetyFinish(Runnable runnable) {
        if(mTitleEditText.getText().toString().isEmpty() && mContentEditText.getText().toString().isEmpty()) {
            runnable.run();
            return;
        }
        showDoYouSureAlert(runnable);
    }

    private void showDoYouSureAlert(final Runnable runnable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.do_you_sure_alert_title);
        builder.setMessage(R.string.do_yout_sure_alert_do_you_want_to_save_change);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
            save();
            runnable.run();
        });
        builder.setNeutralButton(android.R.string.search_go, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setNegativeButton(android.R.string.no, (dialogInterface, i) -> runnable.run());
        builder.show();
    }
}