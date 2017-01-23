package com.example.andrey092009.notepad;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

/**
 * Created by AAhmetov on 20.01.2017.
 */
public class ViewNoteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    TextView tViewNote;
    int id_note =-1;
    DialogDelete dig1 = new DialogDelete();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);



        tViewNote = (TextView) findViewById(R.id.tViewNote);
        id_note = (getIntent().getIntExtra("idNote", 0));
        tViewNote.setText(DBHelper.getNoteBody(new DBHelper(ViewNoteActivity.this).getWritableDatabase(), id_note));

        ImageButton  btnToolbarButton = null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);

        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        super.onBackPressed();
        /*
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_view, menu);
        return true;
    }

    @Override
    protected  void onResume(){
        super.onResume();
        tViewNote.setText(DBHelper.getNoteBody(new DBHelper(ViewNoteActivity.this).getWritableDatabase(), id_note));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent editNoteIntent = new Intent(ViewNoteActivity.this, EditNote.class);
            editNoteIntent.putExtra("idNote", id_note );
            startActivity(editNoteIntent);
            return true;
        }else if(id==R.id.action_delete){
            Bundle args = new Bundle();
            args.putInt("id_note", id_note);
            dig1.setArguments(args);
            ViewNoteActivity.this.dig1.show(getFragmentManager(), "dlg1");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notes) {
            Log.d("AAkhmetov", "itemClick: position = ");
        } else if (id == R.id.nav_about) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void onDestroy() {
        super.onDestroy();
    }

}
