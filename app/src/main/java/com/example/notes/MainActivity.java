package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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

        notes.add("Select note to edit");
        notes.add("Hold note to delete");
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

        // ListView item long click: delete note
        notesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert);
                alert.setTitle("Delete Note").setMessage("Are you sure you want to delete this entire note?");
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
                int noteId = notes.size();
                notes.add("");
                resetCountText();
                startNoteActivity(noteId);
                return true;
            case R.id.settingsItem:
                Toast.makeText(this, "Settings: Not ready yet!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.aboutItem:
                Toast.makeText(this, "About: Not ready yet!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "Error???", Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    // long click: alert dialog ask if delete item

    public void startNoteActivity(int index) {
        Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
        intent.putExtra("noteId", index);
        startActivity(intent);
    }

    public void resetCountText() {
        TextView countTextView = findViewById(R.id.countTextView);
        countTextView.setText("Number of notes: " + notes.size());
    }
}
