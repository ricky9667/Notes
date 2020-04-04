package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    EditText editText;

    public void copyFunction(View view) {
        Toast.makeText(this, "Not Ready Yet!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editText = findViewById(R.id.noteEditText);

        Intent intent = getIntent();

        int noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int noteId = getIntent().getIntExtra("noteId", -1);
                MainActivity.notes.set(noteId, String.valueOf(s));
                MainActivity.adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}