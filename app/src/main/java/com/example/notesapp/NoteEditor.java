package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.data.NoteContract;
import com.example.notesapp.data.NoteDbHelper;
import com.example.notesapp.data.NotesCursorAdapter;

import static com.example.notesapp.MainActivity.ca;
import static com.example.notesapp.MainActivity.listView;
import static com.example.notesapp.MainActivity.notesAdapter;

public class NoteEditor extends AppCompatActivity {
    NoteDbHelper cdbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        Button save = (Button)findViewById(R.id.s);
        EditText title =(EditText)findViewById(R.id.ne);
        EditText description = (EditText)findViewById(R.id.nr);
        cdbh= new NoteDbHelper(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t= title.getText().toString();
                String d = description.getText().toString();


                ContentValues cv = new ContentValues();
                cv.put(NoteContract.NoteEntry.TITLE,t);
                cv.put(NoteContract.NoteEntry.DESCRIPTION,d);

                getContentResolver().insert(NoteContract.NoteEntry.CONTENT_URI,cv);
                String[] projection = {
                        NoteContract.NoteEntry.ID,
                        NoteContract.NoteEntry.TITLE,
                        NoteContract.NoteEntry.DESCRIPTION
                };
                Cursor cr =  getContentResolver().query(NoteContract.NoteEntry.CONTENT_URI,projection,null,null,null);
                NotesCursorAdapter notesCursorAdapter =new NotesCursorAdapter(NoteEditor.this,cr);
                listView.setAdapter(notesCursorAdapter);
                Toast.makeText(NoteEditor.this,"Note added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}