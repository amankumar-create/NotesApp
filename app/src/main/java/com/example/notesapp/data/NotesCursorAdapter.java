package com.example.notesapp.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notesapp.R;


public class NotesCursorAdapter extends CursorAdapter {


    public NotesCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        //Inflate new views initially
        return LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        /*Bind the views with data*/

        TextView tvName= (TextView)view.findViewById(R.id.tvName);
        TextView tvNumber= (TextView)view.findViewById(R.id.tvNumber);

        tvName.setText(cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.TITLE)));
        tvNumber.setText(cursor.getString(cursor.getColumnIndex(NoteContract.NoteEntry.DESCRIPTION)));

    }
}
