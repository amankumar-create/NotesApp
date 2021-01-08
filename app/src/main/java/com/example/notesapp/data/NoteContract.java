package com.example.notesapp.data;

import android.provider.BaseColumns;

public final class NoteContract {
    public static final class NoteEntry implements BaseColumns{

        public static final String TABLE_NAME = "Notes";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
    }
}
