package com.example.andrey092009.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //"?attr/colorPrimary"

    //private Context context = this;
    DBHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    ListView LvNotes;

    String[] items = {"Ирина", "Андрей", "Александр", "Сергей", "Света", "Людмила", "Валентина", "Валентин"
    ,"Ирина", "Андрей", "Александр", "Сергей", "Света", "Людмила", "Валентина", "Валентин", "Людмила"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LvNotes = (ListView) findViewById(R.id.nav_LvNotes);

        LvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Log.d("AAkhmetov", "itemClick: position = " + position + ", id = "
                //        + id);
                Intent noteViewIntent = new Intent(MainActivity.this, ViewNoteActivity.class);
                cursor.moveToPosition(position);
                noteViewIntent.putExtra("idNote", cursor.getInt(cursor.getColumnIndex(DBHelper.ID_COLUMN_NAME)) );
                startActivity(noteViewIntent);
            }
        });


        Log.d("AAhmetov",getDatabasePath(dbHelper.DATABASE_NAME).getPath()+"");
        Log.d("AAhmetov",getDatabasePath(dbHelper.DATABASE_NAME).length()+"");
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        /*
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.BODY_COLUMN_NAME, "ZAM " +
                "Это проверка текста" +
                "на несколько строк" +
                "интернсно как обработается текст" +
                "в ЛистВью и ТекстВью!"
        );
        cv.put(dbHelper.SUBJECT_COLUMN_NAME, "ZAM");
        cv.put(dbHelper.DATE_COLUMN_NAME, "160117");
        db.insert(dbHelper.NOTES_TABLE_NAME, null, cv);
        db.setTransactionSuccessful();
        db.endTransaction();
        */


        Log.d("AAhmetov","SUPER"+ db.getVersion());
        String[] columnsInCursor = {dbHelper.ID_COLUMN_NAME, DBHelper.BODY_COLUMN_NAME};
        cursor = db.query(dbHelper.NOTES_TABLE_NAME, columnsInCursor, null, null, null, null, null);
        cursor.moveToFirst();

        /*
        if(cursor !=null && cursor.moveToFirst()) {
            //Log.d("AAhmetov", cursor.getString(cursor.getColumnIndex(dbHelper.BODY_COLUMN_NAME)));
            while (!cursor.isAfterLast()) {
                Log.d("AAhmetov", cursor.getString(cursor.getColumnIndex(dbHelper.BODY_COLUMN_NAME)));
                cursor.moveToNext();
            }
            cursor.close();
        }
        */

        String[] fromCursor = {dbHelper.ID_COLUMN_NAME,dbHelper.BODY_COLUMN_NAME};
        int[] toAdapter = {R.id.txtView1, R.id.txtView2};
        SimpleCursorAdapter  cursorAdapter = new SimpleCursorAdapter(this, R.layout.my_item_list, cursor, fromCursor, toAdapter);


        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_item_list, items);
        LvNotes.setAdapter(cursorAdapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editNoteIntent = new Intent(MainActivity.this, EditNote.class);
                editNoteIntent.putExtra("idNote", -1 );
                startActivity(editNoteIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
        } else if (id == R.id.nav_about) {
            Intent aboutIntent = new Intent(MainActivity.this, About.class);
            startActivity(aboutIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] columnsInCursor = {dbHelper.ID_COLUMN_NAME, DBHelper.BODY_COLUMN_NAME};
        cursor = db.query(dbHelper.NOTES_TABLE_NAME, columnsInCursor, null, null, null, null, null);
        cursor.moveToFirst();

        String[] fromCursor = {dbHelper.ID_COLUMN_NAME,dbHelper.BODY_COLUMN_NAME};
        int[] toAdapter = {R.id.txtView1, R.id.txtView2};
        SimpleCursorAdapter  cursorAdapter = new SimpleCursorAdapter(this, R.layout.my_item_list, cursor, fromCursor, toAdapter);
        LvNotes.setAdapter(cursorAdapter);
        // закрываем подключение при выходе
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
        // закрываем подключение при выходе
    }





}
