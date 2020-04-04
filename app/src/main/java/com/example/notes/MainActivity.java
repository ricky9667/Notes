package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter<String> adapter;
    ListView notesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        notesListView = findViewById(R.id.notesListView);

        notes.add("Select to edit note");
        notes.add("Hold to delete note");
        setCountText(notes.size());

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(adapter);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openNoteActivity(position);
            }
        });
    }

    // menu setup
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.addItem:
//                Toast.makeText(this, "Add note", Toast.LENGTH_SHORT).show();
                int noteId = notes.size();
                notes.add("");
                setCountText(notes.size());
                openNoteActivity(noteId);
                return true;
            case R.id.settingsItem:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.aboutItem:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "Error???", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    // long click: alert dialog ask if delete item

    public void openNoteActivity(int index) {
        Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
        intent.putExtra("noteId", index);
        startActivity(intent);
    }

    public void setCountText(int count) {
        TextView countTextView = findViewById(R.id.countTextView);
        countTextView.setText("Number of notes: " + count);
    }
}
