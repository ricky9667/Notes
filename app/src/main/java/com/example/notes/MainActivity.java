package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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

        try {
            notes = (ArrayList<String>) ObjectSerializer.deserialize(
                    sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        resetCountText();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);

        // ListView setup
        notesListView.setAdapter(adapter);

        // ListView item click: edit note
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startNoteActivity(position);
            }
        });

        // ListView item long click: delete note with alert dialog
        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                alert.setIcon(android.R.drawable.ic_menu_delete)
                     .setTitle("Delete Note")
                     .setMessage("Are you sure you want to delete this note?");

                alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notes.remove(position);
                        resetCountText();
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Cancel", null);

                alert.show();
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveNotes();
    }

    // Menu setup
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Menu item click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.addItem:
                notes.add("");
                startNoteActivity(notes.size() - 1);
                resetCountText();
                return true;
            case R.id.aboutItem:
                Toast.makeText(this, "About: Not ready yet!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    // Start NoteActivity with index
    public void startNoteActivity(int index) {
        Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
        intent.putExtra("noteId", index);
        startActivity(intent);
    }

    // Reset "Number of notes: "
    public void resetCountText() {
        TextView countTextView = findViewById(R.id.countTextView);
        countTextView.setText("Number of notes: " + notes.size());
    }

    public void saveNotes() {
        try {
            sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(notes)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // delete all notes
    public void deleteAll(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        alert.setIcon(android.R.drawable.ic_delete)
                .setTitle("Delete All Notes")
                .setMessage("Are you sure you want to delete all your notes?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notes.clear();
                resetCountText();
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("No", null);

        alert.show();

    }
}
