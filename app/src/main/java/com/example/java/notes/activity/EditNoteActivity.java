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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.java.notes.R;
import com.example.java.notes.adapters.NotesFragmentPagerAdapter;
import com.example.java.notes.db.NotesContract;
import com.example.java.notes.model.Note;
import com.example.java.notes.util.DateUtil;
import com.tjeannin.provigen.ProviGenBaseContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String RESULT =  "RESULT";
    private static final String SHARE_TYPE = "text/plain";

    private long mId = -1;
    private Boolean mIsNoteUpdatable = false;
    private String mOriginalTitle = "";
    private String mOriginalText = "";


    @BindView(R.id.toolbar)
    protected Toolbar mToolbar = null;
    @BindView(R.id.view_pager)
    protected ViewPager mViewPager = null;

    private NotesFragmentPagerAdapter mViewPagerAdapter = null;


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
        mViewPagerAdapter = new NotesFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        setTitle("Note");
    }

    private void checkIntentByExtraId() {
        Intent intent = getIntent();
        if(!intent.hasExtra(ProviGenBaseContract._ID)) return;
        mId = intent.getLongExtra(ProviGenBaseContract._ID, mId);
        if(mId == -1) return;
        mIsNoteUpdatable = true;
        getLoaderManager().initLoader(R.id.edit_note_loader, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                safetyFinish(() -> finish());
                break;
            }
            case R.id.action_share: {
//                share();;
                break;
            }
            case R.id.action_delete:{
                deleteNote();
                Intent intent = new Intent();
                intent.putExtra(RESULT, "Удалено");
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

//    private void share() {
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_TEXT, prepareNoteForSharing());
//        shareIntent.setType(SHARE_TYPE);
//        startActivity(shareIntent);
//    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        menu.findItem(R.id.action_share).setVisible(mIsNoteUpdatable);
        menu.findItem(R.id.action_delete).setVisible(mIsNoteUpdatable);
        return super.onCreatePanelMenu(featureId, menu);
    }

//    private String prepareNoteForSharing() {
//        return getString(R.string.sharing_template, mTitleEditText.getText(), mContentEditText.getText());
//    }

//    @OnClick(R.id.saveBtn)
//    public void onSaveBtnClick() {
//        save();
//        finish();
//    }

//    private void insertNote() {
//        ContentValues values = new ContentValues();
//        values.put(NotesContract.TITLE_COLUMN, mTitleEditText.getText().toString());
//        values.put(NotesContract.TEXT_COLUMN, mContentEditText.getText().toString());
//        values.put(NotesContract.TIME_COLUMN, DateUtil.formatCurrentDate());
//        getContentResolver().insert(NotesContract.CONTENT_URI, values);
//    }

//    private void updateNote() {
//        ContentValues values = new ContentValues();
//        values.put(NotesContract.TITLE_COLUMN, mTitleEditText.getText().toString());
//        values.put(NotesContract.TEXT_COLUMN, mContentEditText.getText().toString());
//        values.put(NotesContract.TIME_COLUMN, DateUtil.formatCurrentDate());
//        getContentResolver().update(
//                Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(mId)),
//                values,
//                null,
//                null);
//    }

    private void deleteNote() {
        getContentResolver().delete(
                Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(mId)),
                null,
                null);
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                this,
                NotesContract.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) return;
        Note note = new Note(cursor);
        List<Note> dataSource = new ArrayList<>();
        do {
            dataSource.add(new Note(cursor));
        } while (cursor.moveToNext());

        mViewPagerAdapter.setDataSource(dataSource);

//        mTitleEditText.setText(note.getTitle());
//        mContentEditText.setText(note.getText());
        mOriginalTitle = note.getTitle();
        mOriginalText = note.getText();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

//    @Override
//    public void onBackPressed() {
//        safetyFinish(() -> EditNoteActivity.super.onBackPressed());
//    }

    private void safetyFinish(Runnable finish) {
//        if(mOriginalTitle.equals(mTitleEditText.getText().toString())
//                && mOriginalText.equals(mContentEditText.getText().toString())) {
//            finish.run();
//            return;
//        }
        showDoYouSureAlert(finish);
    }

    private void showDoYouSureAlert(final Runnable finish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.do_you_sure_alert_title);
        builder.setMessage(R.string.do_yout_sure_alert_do_you_want_to_save_change);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
            save();
            finish.run();
        });
        builder.setNeutralButton(android.R.string.search_go, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setNegativeButton(android.R.string.no, (dialogInterface, i) -> finish.run());
        builder.show();
    }

    private void save() {
        if(mIsNoteUpdatable) {
//            updateNote();
        } else {
//            insertNote();
        }
    }
}
