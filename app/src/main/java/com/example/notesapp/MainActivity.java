package com.example.notesapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.notesapp.data.NoteContract;
import com.example.notesapp.data.NoteContract.NoteEntry;
import com.example.notesapp.data.NoteDbHelper;
import com.example.notesapp.data.NotesCursorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static NotesCursorAdapter notesAdapter;
    static NotesAdapter ca ;
    static ListView listView;
    NoteDbHelper cdbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        cdbh= new NoteDbHelper(this);
        listView= (ListView) findViewById(R.id.lv);
        display();




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,NoteEditor.class);
                startActivity(intent);
            }
        });



    }
    public void display(){

        String[] projection = {
                  NoteEntry.ID,
                NoteEntry.TITLE,
                NoteEntry.DESCRIPTION
        };

        Cursor cr =  getContentResolver().query(NoteEntry.CONTENT_URI,projection,null,null,null);
        // Find ListView to populate
        // Setup cursor adapter using cursor from last step
         notesAdapter = new NotesCursorAdapter(this,cr);
        // Attach cursor adapter to the ListView
        listView.setAdapter(notesAdapter);



        
    }




}