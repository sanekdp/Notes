package com.example.java.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.java.notes.R;
import com.example.java.notes.adapters.NotesAdapter;
import com.example.java.notes.model.Note;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Notes extends AppCompatActivity {

    @BindView(R.id.recycler_notes)
    protected RecyclerView recyclerView = null;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar = null;
    @BindView(R.id.floating_btn)
    protected FloatingActionButton mFabButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
        NotesAdapter adapter = new NotesAdapter();
        List<Note> dataSource = new ArrayList<>();
        for (int i = 0; i < 100;i++){
            Note note = new Note();
            note.setTime(System.currentTimeMillis());
            note.setText("title : " + i);
            note.setTitle("Title: " + i);
            dataSource.add(note);
        }
        recyclerView.setAdapter(adapter);
        adapter.setDataSource(dataSource);
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EditNote.newInstance(Notes.this);
                intent.putExtra(EditNote.EDIT_FIRST_TEXT_KEY, "put Data Key");
                startActivity(intent);
            }
        });

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
}
