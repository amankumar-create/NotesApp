package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.data.NoteContract;
import com.example.notesapp.data.NoteDbHelper;

public class NoteEditor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Uri uri;
    EditText title;
    EditText description;
    Boolean editing = false ,adding =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        Intent intent = getIntent();
        uri =intent.getData();
        Button save = (Button)findViewById(R.id.s);
        title =(EditText)findViewById(R.id.ne);
        description = (EditText)findViewById(R.id.nr);

        if(uri!=null){
            setTitle("Edit Note");
            getSupportLoaderManager().initLoader(0, null,this);
            editing=true;
        }
        else{
            setTitle("Add new Note");
            adding=true;
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t= title.getText().toString();
                String d = description.getText().toString();


                ContentValues cv = new ContentValues();
                cv.put(NoteContract.NoteEntry.TITLE,t);
                cv.put(NoteContract.NoteEntry.DESCRIPTION,d);
                if(editing==true){
                    getContentResolver().update(uri,cv,null,null);
                }
                else {
                    getContentResolver().insert(NoteContract.NoteEntry.CONTENT_URI, cv);
                    Toast.makeText(NoteEditor.this, "Note added", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, uri ,new String[]{
                NoteContract.NoteEntry.ID,
                NoteContract.NoteEntry.TITLE,
                NoteContract.NoteEntry.DESCRIPTION},null,null,null);
    }



    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1) {
            return;
        }
        if (data.moveToFirst()) {
            title.setText(data.getString(data.getColumnIndex(NoteContract.NoteEntry.TITLE)));
            description.setText(data.getString(data.getColumnIndex(NoteContract.NoteEntry.DESCRIPTION)));
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        title.setText("");
        description.setText("");
    }


}