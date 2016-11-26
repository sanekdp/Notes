package com.example.java.notes.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.java.notes.R;
import com.example.java.notes.activity.DisplayNoteActivity;
import com.example.java.notes.activity.EditNoteActivity;
import com.example.java.notes.activity.NotesActivity;
import com.example.java.notes.db.NotesContract;
import com.example.java.notes.model.Note;
import com.tjeannin.provigen.ProviGenBaseContract;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class NoteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static NoteFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(ProviGenBaseContract._ID, id);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.title_note_text)
    protected TextView mTitleNote;
    @BindView(R.id.content_note_text)
    protected TextView mContentNote;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(
                R.layout.fragment_note,
                container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity()
                .getSupportLoaderManager()
                .initLoader((int)getArguments().getLong(ProviGenBaseContract._ID), null, this);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                getActivity().finish();
                break;
            }
            case R.id.action_delete:
                deleteNote();
                break;
            case R.id.action_edit: {
                long idNote = getArguments().getLong(ProviGenBaseContract._ID);
                startActivity(EditNoteActivity.newInstance(getContext(), idNote));
                break;
            }
            case R.id.action_share: {
                share();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, prepareNoteForSharing());
        shareIntent.setType(DisplayNoteActivity.SHARE_TYPE);
        startActivity(shareIntent);
    }

    private String prepareNoteForSharing() {
        return getString(R.string.sharing_template, mTitleNote.getText(), mContentNote.getText());
    }

    private void deleteNote() {
        FragmentActivity frActivity = getActivity();
        frActivity.getContentResolver().delete(
                Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(getArguments().getLong(ProviGenBaseContract._ID))),
                null,
                null);
        Intent intent = new Intent();
        intent.putExtra(NotesActivity.RESULT_DELETE_KEY, "");
        frActivity.setResult(RESULT_OK, intent);
        frActivity.finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long noteId = getArguments().getLong(ProviGenBaseContract._ID);
        return new CursorLoader(
                getActivity(),
                Uri.withAppendedPath(NotesContract.CONTENT_URI, String.valueOf(noteId)),
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || !data.moveToFirst()) return;
        Note note = new Note(data);
        mTitleNote.setText(note.getTitle());
        mContentNote.setText(note.getText());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
