package com.example.notesapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.notesapp.data.NoteContract;
import com.example.notesapp.data.NoteContract.NoteEntry;
import com.example.notesapp.data.NoteDbHelper;
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

    static ArrayList<Note> Notes = new ArrayList<>();;
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

        String s= Integer.toString(Notes.size());
        Log.d("size",s);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,NoteEditor.class);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Notes.remove(position);
                ca= new NotesAdapter(MainActivity.this,Notes);
                listView.setAdapter(ca);
                SQLiteDatabase sdb= cdbh.getWritableDatabase();
                SQLiteDatabase cdb = cdbh.getReadableDatabase();
                Cursor cr = cdb.rawQuery("SELECT * FROM "+ NoteEntry.TABLE_NAME,null);
                cr.moveToFirst();
                String first;
                first= cr.getString(cr.getColumnIndex("id"));
                sdb.delete(NoteEntry.TABLE_NAME, "id = ?", new String[]{String.valueOf(position+Integer.parseInt(first))});

                return false;
            }
        });


    }
    public void display(){
        Notes.clear();

        SQLiteDatabase cdb = cdbh.getReadableDatabase();
        String cTitle ;
        String cDescription;
        String[] projection = {

                NoteEntry.TITLE,
                NoteEntry.DESCRIPTION
        };
        getContentResolver().query(NoteEntry.CONTENT_URI,projection,null,null,null);
        Cursor cr =  getContentResolver().query(NoteEntry.CONTENT_URI,projection,null,null,null);

        if(cr.getCount()!=0) {
            cr.moveToFirst();
            cTitle = cr.getString(cr.getColumnIndex(NoteEntry.TITLE));
            cDescription = cr.getString(cr.getColumnIndex(NoteEntry.DESCRIPTION));
            Notes.add(new Note(cTitle, cDescription));
            while (cr.moveToNext()) {
                cTitle = cr.getString(cr.getColumnIndex(NoteEntry.TITLE));
                cDescription = cr.getString(cr.getColumnIndex(NoteEntry.DESCRIPTION));
                Notes.add(new Note(cTitle, cDescription));
            }
        }
        cr.close();

        ca = new NotesAdapter(this,Notes);
        listView.setAdapter(ca);
        
    }




}