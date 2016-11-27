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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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

public class DisplayNoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private long mCurrentNoteId = -1;
    public static final String SHARE_TYPE = "text/plain";
    public static final int REQUEST_CODE_EDIT = 102;
    public static final String DEFAULT_RESULT = "Результат не известен";
    public static final String CHANGED = "Изменено";
//    private long mId = -1;
    private NotesFragmentPagerAdapter mViewPagerAdapter = null;

    @BindView(R.id.view_pager)
    protected ViewPager mViewPager;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar = null;

    @NonNull
    public static Intent newInstance(@NonNull Context context, long id) {
        Intent intent = new Intent(context, DisplayNoteActivity.class);
        intent.putExtra(ProviGenBaseContract._ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_note);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.note_display);
        mCurrentNoteId = Long.valueOf(getIntent().getLongExtra(ProviGenBaseContract._ID, -1));
        getLoaderManager().initLoader(R.id.edit_note_loader, null, this);
        mViewPagerAdapter = new NotesFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String result = DEFAULT_RESULT;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT )
            result = CHANGED;
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
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
        List<Note> dataSource = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                dataSource.add(new Note(cursor));
            } while (cursor.moveToNext());
        }
        mViewPagerAdapter.setDataSource(dataSource);
        if (cursor != null && cursor.moveToFirst()) {
            int numStartItem = -1;
            for (Note note : dataSource) {
                numStartItem++;
                if (note.getId() == mCurrentNoteId)
                    break;
            }
            mViewPager.setCurrentItem(numStartItem);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}