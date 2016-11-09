package com.example.java.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.java.notes.R;

public class EditNote extends AppCompatActivity {

    public static Intent newInstance(Context context){
        return new Intent(context, EditNote.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        String extraString()
    })
}
