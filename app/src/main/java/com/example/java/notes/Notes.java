package com.example.java.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.java.notes.adapters.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class Notes extends AppCompatActivity {

    private RecyclerView recyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_notes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
        NotesAdapter adapter = new NotesAdapter();
        List<String> dataSource = new ArrayList<String>();
        for (int i = 0; i < 100;i++){
            dataSource.add("title: " + i);
        }
        recyclerView.setAdapter(adapter);
        adapter.setDataSource(dataSource);

    }
}
