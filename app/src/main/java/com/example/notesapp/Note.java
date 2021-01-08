package com.example.notesapp;

public class Note {
    private String name;
    private String number;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
    public Note(String n, String m){
        name =n;
        number =m;
    }
}
