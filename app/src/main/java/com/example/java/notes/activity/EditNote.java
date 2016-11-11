package com.example.java.notes.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.java.notes.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditNote extends AppCompatActivity {

    @BindView(R.id.first_edit_text)
    protected EditText mFirstEditText = null;
    @BindView(R.id.second_edit_text)
    protected EditText mSecondEditText = null;

    public static final String EDIT_FIRST_TEXT_KEY = "EDIT_FIRST_TEXT_KEY";
    public static final String EDIT_SECOND_TEXT_KEY = "EDIT_SECOND_TEXT_KEY";

    public static Intent newInstance(Context context){
        return new Intent(context, EditNote.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);
        String extraTitle = getIntent().getStringExtra(EDIT_FIRST_TEXT_KEY);
        String extraText = getIntent().getStringExtra(EDIT_SECOND_TEXT_KEY);

        mFirstEditText.setText(extraTitle);
        mSecondEditText.setText(extraText);
    }
}
