package com.example.java.notes.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.java.notes.R;
import com.example.java.notes.db.NotesContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditNoteActivity extends AppCompatActivity {

    private static final String SHARE_TYPE = "text/plain";
    public static final String RESULT =  "RESULT";

    @BindView(R.id.titleEditText)
    protected EditText mFirstEditText;
    @BindView(R.id.contentEditText)
    protected EditText mSecondEditText;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar = null;

    public static final String EDIT_FIRST_TEXT_KEY = "EDIT_FIRST_TEXT_KEY";
    public static final String EDIT_SECOND_TEXT_KEY = "EDIT_SECOND_TEXT_KEY";

    public static Intent newInstance(Context context) {
        return new Intent(context, EditNoteActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        setTitle("Edit Note");
//        String extraTitle = getIntent().getStringExtra(EDIT_FIRST_TEXT_KEY);
//        String extraText = getIntent().getStringExtra(EDIT_SECOND_TEXT_KEY);
//        mFirstEditText.setText(extraTitle);
//        mSecondEditText.setText(extraText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                //finish();
                Intent intent = new Intent(this, NotesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;
            }
            case R.id.action_share: {
                //share();

                Intent intent = new Intent();
                intent.putExtra(RESULT, prepareNoteForSharing());
                setResult(RESULT_OK, intent);
                finish();

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, prepareNoteForSharing());
        shareIntent.setType(SHARE_TYPE);
        startActivity(shareIntent);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return super.onCreatePanelMenu(featureId, menu);
    }

    private String prepareNoteForSharing() {
        return getString(R.string.sharing_template, mFirstEditText.getText(), mSecondEditText.getText());
    }

    @OnClick(R.id.saveBtn)
    public void onSaveBtnClick() {
        insertNote();
        finish();
    }

    private void insertNote() {
        ContentValues values = new ContentValues();
        values.put(NotesContract.TITLE_COLUMN, mFirstEditText.getText().toString());
        values.put(NotesContract.TEXT_COLUMN, mSecondEditText.getText().toString());
        values.put(NotesContract.TIME_COLUMN, String.valueOf(System.currentTimeMillis()));
        getContentResolver().insert(NotesContract.CONTENT_URI, values);
    }
}
