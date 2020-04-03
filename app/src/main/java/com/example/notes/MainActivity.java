package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ListView notesListView;
    ArrayList<String> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        notesListView = findViewById(R.id.notesListView);

        notes = new ArrayList<>();
        notes.add("Blank Note");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(adapter);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, notes.get(position), Toast.LENGTH_SHORT).show();
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
            case R.id.addNote:
                Toast.makeText(this, "Add note", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.clearAllNotes:
                Toast.makeText(this, "Clear all notes", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "Error???", Toast.LENGTH_SHORT).show();
                return false;
        }
    }
}
