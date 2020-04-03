package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    EditText noteEditText;

    public void copyFunction(View view) {
        Toast.makeText(this, "Not Ready Yet!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteEditText = findViewById(R.id.noteEditText);

        Intent intent = getIntent();
        String note = intent.getStringExtra("note");

        noteEditText.setText(note);
    }
}