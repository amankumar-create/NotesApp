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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
        uri =intent.getData();  // uri attached with the intent which is of the long pressed note in main activity
        ImageView save = (ImageView) findViewById(R.id.s);
        title =(EditText)findViewById(R.id.ne);
        description = (EditText)findViewById(R.id.nr);
        TextView action = (TextView)findViewById(R.id.action);
        if(uri!=null){
            getSupportLoaderManager().initLoader(0, null,this);
            action.setText("Edit Pet");
            editing=true;
        }
        else{
            action.setText("Add new Note");
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
                    if(!(TextUtils.isEmpty(t)&&TextUtils.isEmpty(d)))
                    getContentResolver().update(uri,cv,null,null);
                    else
                        getContentResolver().delete(uri,null,null); // if both title and description of note are empty then delete the note
                }
                else {
                    if(!(TextUtils.isEmpty(t)&&TextUtils.isEmpty(d))) {
                        getContentResolver().insert(NoteContract.NoteEntry.CONTENT_URI, cv);
                        Toast.makeText(NoteEditor.this, "Note added", Toast.LENGTH_SHORT).show();
                    }
                    else  Toast.makeText(NoteEditor.this, "Note was empty hence not added", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
        ImageView del = (ImageView)findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().delete(uri,null,null); // delete the note when delete view is clicked
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
                NoteContract.NoteEntry.DESCRIPTION},null,null,null); // create the loader with the  uri
    }



    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1) {
            return;
        }
        if (data.moveToFirst()) {
            title.setText(data.getString(data.getColumnIndex(NoteContract.NoteEntry.TITLE))); // set the data to edit text (Title) after loading
            description.setText(data.getString(data.getColumnIndex(NoteContract.NoteEntry.DESCRIPTION)));// set the data to edit text(desciption)  after loading
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        title.setText("");
        description.setText("");
    }


}