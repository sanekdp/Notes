package com.example.java.notes.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.java.notes.R;
import com.example.java.notes.adapters.NotesAdapter;
import com.example.java.notes.adapters.NotesAdapter.NotesViewHolder;
import com.example.java.notes.db.NotesContract;
import com.example.java.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, View.OnLongClickListener {

    private static final int REQUEST_CODE = 101;
    @BindView(R.id.recycler_notes)
    protected RecyclerView recyclerView = null;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar = null;
    @BindView(R.id.floating_btn)
    protected FloatingActionButton mFabButton = null;


    @OnClick(R.id.floating_btn)
    void onClick() {
        startActivityForResult(EditNoteActivity.newInstance(this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CODE) {
                String result = data.getStringExtra(EditNoteActivity.RESULT);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        getSupportLoaderManager().initLoader(R.id.notes_loader, null, this);

        for (int i = 0; i < 10; i++ )
        {
            ContentValues values = new ContentValues();
            values.put(NotesContract.TEXT_COLUMN, "jfgdkfjg" + i);
            getContentResolver().insert(NotesContract.CONTENT_URI, values);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Snackbar.make(recyclerView, R.string.settings, Snackbar.LENGTH_LONG).show();
                return true;
            case R.id.action_help:
                Snackbar.make(
                        recyclerView,
                        R.string.help,
                        Snackbar.LENGTH_LONG)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                NotesContract.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<Note> dataSource = new ArrayList<>();
        if (data.getCount() == 0) return;
        data.moveToFirst();
        do
        {
            dataSource.add(new Note(data));
        }
        while (data.moveToNext());
        NotesAdapter adapter = new NotesAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setDataSource(dataSource);
        adapter.setOnItemClickListener(this);
        adapter.setmOnLongItemClickListener(this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {
        NotesViewHolder holder = (NotesViewHolder) recyclerView.findContainingViewHolder(view);
        if (holder == null) return;;
        startActivity(EditNoteActivity.newInstance(this, holder.getNote().getId()));
    }


    @Override
    public boolean onLongClick(View view) {
        showPopupMenu(view);
        return true;
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popupmenu);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.action_delete:
                    return deleteNote(view);
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    private boolean deleteNote(View view) {
        NotesViewHolder holder = (NotesViewHolder) recyclerView.findContainingViewHolder(view);
        if (holder != null) {
            long idNote = holder.getNote().getId();
            getContentResolver().delete(
                    Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(idNote)),
                    null,
                    null);
        }
        return true;
    }
}
