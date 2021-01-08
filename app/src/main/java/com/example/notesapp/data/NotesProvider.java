package com.example.notesapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NotesProvider extends ContentProvider {
    private NoteDbHelper mdbhelper;
    public static final int NOTES=100;
    public static final int NOTE_ID =101;
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
                cursor = db.query(NoteContract.NoteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case NOTE_ID:
                selection = "id=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(NoteContract.NoteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                cursor=null;
                break;
        }

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
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
