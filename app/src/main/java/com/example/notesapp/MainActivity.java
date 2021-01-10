package com.example.notesapp;

import android.content.ContentUris;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    static NotesCursorAdapter notesAdapter;
    static NotesAdapter ca ;
    static ListView listView;
    NoteDbHelper cdbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportLoaderManager().initLoader(0, null, this);



        listView= (ListView) findViewById(R.id.lv);

         notesAdapter= new NotesCursorAdapter(this,null);
         listView.setAdapter(notesAdapter);

         listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                 Intent intent = new Intent(MainActivity.this,NoteEditor.class);
                 intent.setData(new ContentUris().withAppendedId(NoteEntry.CONTENT_URI,id));
                 startActivity(intent);
                 return false;
             }
         });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,NoteEditor.class);
                startActivity(intent);
            }
        });



    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this,NoteEntry.CONTENT_URI,new String[]{
                NoteEntry.ID,
                NoteEntry.TITLE,
                NoteEntry.DESCRIPTION},null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
          notesAdapter.swapCursor(data);
        Log.i("TAG", "onLoadFinished: ");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
          notesAdapter.swapCursor(null);
        Log.i("TAG", "onLoaderReset: ");
    }
}