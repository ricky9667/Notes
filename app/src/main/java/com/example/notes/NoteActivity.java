package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    EditText editText;
    int noteId;

    public void copyFunction(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copiedString", editText.getText().toString());

        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(this, "Copied note to clipboard", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        editText = findViewById(R.id.noteEditText);

        noteId = getIntent().getIntExtra("noteId", -1);

        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveText(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void saveText(String text) {
        MainActivity.notes.set(noteId, text);
        MainActivity.adapter.notifyDataSetChanged();
    }
}