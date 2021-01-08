package com.example.notesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<Note> {
    public NotesAdapter(@NonNull Context context,  @NonNull List<Note> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note thisNote = getItem(position);
        View listItem= convertView;
        if(listItem==null){
            listItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView tv1 = (TextView)listItem.findViewById(R.id.tvName);
        TextView tv2 =(TextView)listItem.findViewById(R.id.tvNumber);
        tv1.setText(thisNote.getName());
        tv2.setText(thisNote.getNumber());
        return listItem;
    }
}
