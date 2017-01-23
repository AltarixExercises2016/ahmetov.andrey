package com.example.andrey092009.notepad;

import android.database.Cursor;

/**
 * Created by 1 on 15.01.2017.
 */
public class Note {
    private final int id;
    private final String body;

    public Note(int id, String body) {
        this.id = id;
        this.body = body;
    }

    public static Note fromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex("_id"));
        String name = c.getString(c.getColumnIndex("name"));
        return new Note(id, name);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return body;
    }


}
