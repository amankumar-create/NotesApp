package com.example.notesapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class NoteContract {
    public static final String CONTENT_AUTHORITY = "com.example.notesapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_NOTES = "pets";

    public static final class NoteEntry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NOTES);
        public static final String ID ="_id";
        public static final String TABLE_NAME = "Notes";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
    }
}
