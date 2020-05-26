package com.prismdawn.b10709028_hw2;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.prismdawn.b10709028_hw2.Data.SpwContract.SpwEntry;

public class AddActivity extends AppCompatActivity {

    private static final String LOG_TAG = AddActivity.class.getSimpleName();
    private EditText mNewNameEditText;
    private EditText mNewSizeEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mNewNameEditText = findViewById(R.id.name_edit_text);
        mNewSizeEditText = findViewById(R.id.count_edit_text);

    }

    public void addTolist(View view) {
        if (mNewNameEditText.getText().length() == 0 ||
                mNewSizeEditText.getText().length() == 0) {
            return;
        }
        int size = 1;
        try {
            size = Integer.parseInt(mNewSizeEditText.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse size text to number: " + ex.getMessage());
        }

        addNewName(mNewNameEditText.getText().toString(), size);

        mNewSizeEditText.clearFocus();
        mNewNameEditText.getText().clear();
        mNewSizeEditText.getText().clear();
        finish();
    }

    public void cancelTolist(View view) {
        mNewSizeEditText.clearFocus();
        mNewNameEditText.getText().clear();
        mNewSizeEditText.getText().clear();
        finish();
    }


    private void addNewName(String name, int size) {
        ContentValues cv = new ContentValues();
        cv.put(SpwEntry.COLUMN_NAME, name);
        cv.put(SpwEntry.COLUMN_SIZE, size);
        getContentResolver().insert(SpwEntry.CONTENT_URI, cv);
    }
}
