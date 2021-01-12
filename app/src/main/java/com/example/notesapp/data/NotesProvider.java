package com.example.notesapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NotesProvider extends ContentProvider {
    private NoteDbHelper mdbhelper;
    public static final int NOTES=100;  // Integer code for uri type pointing whole notes table
    public static final int NOTE_ID =101; // Integer code for uri type pointing whole notes table
    public static final UriMatcher sUrimatcher= new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUrimatcher.addURI(NoteContract.CONTENT_AUTHORITY,NoteContract.PATH_NOTES, NOTES ); //URI FOR WHOLE NOTES TABLE
        sUrimatcher.addURI(NoteContract.CONTENT_AUTHORITY,NoteContract.PATH_NOTES+"/#", NOTE_ID);  //URI FOR A SPECIFIC NOTE THROUGH ID
    }
    @Override
    public boolean onCreate() {
        mdbhelper= new NoteDbHelper(getContext());
        return false;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = mdbhelper.getReadableDatabase();
        Cursor cursor;
        int match = sUrimatcher.match(uri);
        switch (match) {
            case NOTES:

                //If uri points to whole pets table

                cursor = db.query(NoteContract.NoteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);//query mehod on database
                break;
            case NOTE_ID:

                // If uri points to a specific row in table

                selection = NoteContract.NoteEntry.ID+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(NoteContract.NoteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder); //query mehod on database
                break;
            default:
                cursor=null;
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match =sUrimatcher.match(uri);
        SQLiteDatabase db = mdbhelper.getWritableDatabase();
        switch (match){
            case NOTES:
                long id =db.insert(NoteContract.NoteEntry.TABLE_NAME,null,values);
                getContext().getContentResolver().notifyChange(uri,null);  // to notify change in database where uri points
                Log.i("change notified", "insert: ");
                return ContentUris.withAppendedId(uri,id);

        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUrimatcher.match(uri);
        SQLiteDatabase db= mdbhelper.getWritableDatabase();
        switch(match){
            case NOTE_ID:
                selection = NoteContract.NoteEntry.ID+"=?";
                int n= db.delete(NoteContract.NoteEntry.TABLE_NAME,selection,new String[]{String.valueOf(ContentUris.parseId(uri))});
                getContext().getContentResolver().notifyChange(uri,null);// to notify change in database where uri points
                break;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUrimatcher.match(uri);
        SQLiteDatabase db= mdbhelper.getWritableDatabase();
        switch(match){
            case NOTE_ID:
                selection = NoteContract.NoteEntry.ID+"=?";
                int n = db.update(NoteContract.NoteEntry.TABLE_NAME,values,selection,new String[]{String.valueOf(ContentUris.parseId(uri))}); //update method on database object
                getContext().getContentResolver().notifyChange(uri,null);// to notify change in database where uri points
                break;
         }
        return 0;
    }
}
