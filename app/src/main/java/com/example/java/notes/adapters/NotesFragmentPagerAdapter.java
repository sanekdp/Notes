package com.example.java.notes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.java.notes.fragments.NoteFragment;
import com.example.java.notes.model.Note;

import java.util.List;

public class NotesFragmentPagerAdapter extends FragmentPagerAdapter {


    private List<Note> mDataSource = null;

    public NotesFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        long id = mDataSource.get(position).getId();
        return NoteFragment.newInstance(id);
    }

    @Override
    public int getCount() {
        return mDataSource == null ? 0 : mDataSource.size();
    }

    public void setDataSource(List<Note> dataSource) {
        mDataSource = dataSource;
        notifyDataSetChanged();
    }
}
