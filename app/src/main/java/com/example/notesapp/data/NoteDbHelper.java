package com.example.notesapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.notesapp.data.NoteContract.NoteEntry;

public class NoteDbHelper extends SQLiteOpenHelper {
    private static final String db_name = "Notes.db";
    private static final int db_version = 1;
    public NoteDbHelper(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE "+ NoteEntry.TABLE_NAME+ "("+NoteEntry.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ NoteEntry.TITLE+" TEXT, "+NoteEntry.DESCRIPTION+" TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
