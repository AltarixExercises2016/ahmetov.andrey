package com.example.andrey092009.notepad;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditNote extends AppCompatActivity {

    int id_note;
    DBHelper dbHelper;
    SQLiteDatabase db;
    EditText editNote;
    DialogDelete dig1 = new DialogDelete();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        id_note = getIntent().getIntExtra("idNote", -1);
        if(id_note == -1){
            setTitle("Создание");
        }
        else setTitle("Редактирование");

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        editNote = (EditText) findViewById(R.id.edit_note);
        editNote.setText(DBHelper.getNoteBody(db, id_note));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.m_save_note:
                if(!editNote.getText().toString().trim().equals("")) {
                    DBHelper.insertNote(new DBHelper(this).getWritableDatabase(), id_note, editNote.getText().toString());
                    finish();
                }     else if (id_note!=-1){
                    Bundle args = new Bundle();
                    args.putInt("id_note", id_note);
                    dig1.setArguments(args);
                    EditNote.this.dig1.show(getFragmentManager(), "dlg1");
                }
                else finish();
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
