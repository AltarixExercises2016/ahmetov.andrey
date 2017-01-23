package com.example.andrey092009.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by AAhmetov on 15.01.2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME="DBNotes";
    public static final String NOTES_TABLE_NAME = "tbl_notes";
    public static final String ID_COLUMN_NAME = "_id";
    public static final String BODY_COLUMN_NAME = "body";
    public static final String SUBJECT_COLUMN_NAME = "subject";
    public static final String DATE_COLUMN_NAME = "date";

    private static final String ID_COLUMN_TYPE = "integer PRIMARY KEY";
    private static final String BODY_COLUMN_TYPE = "text not null";
    private static final String SUBJECT_COLUMN_TYPE = "text";
    private static final String DATE_COLUMN_TYPE = "text";

    private static final String NOTES_TABLE_CREATE =
            "CREATE TABLE " + NOTES_TABLE_NAME + " (" +
                    ID_COLUMN_NAME + " " +ID_COLUMN_TYPE+", "+
                    BODY_COLUMN_NAME + " " + BODY_COLUMN_TYPE + ", "+
                    SUBJECT_COLUMN_NAME + " " + SUBJECT_COLUMN_TYPE + ", "+
                    DATE_COLUMN_NAME + " " + DATE_COLUMN_TYPE + " "+
                    ");";

    private static final String TRIGGER_T_TBL_NOTE_B_I = "t_tbl_note_b_i";
    private static final String CREATE_TRIGGER_T_TBL_NOTE_B_I=
            "CREATE TRIGGER "+TRIGGER_T_TBL_NOTE_B_I+ "AFTER INSERT ON " + NOTES_TABLE_NAME +
            " FOR EACH ROW " +
            "BEGIN " +
            "UPDATE " + NOTES_TABLE_NAME +" SET " + DATE_COLUMN_NAME + " = CURRENT_TIMESTAMP "+
            "WHERE " + ID_COLUMN_NAME + "= new." + ID_COLUMN_NAME + ";" +
            "END;";
    public static int Check=0;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NOTES_TABLE_CREATE);
        db.execSQL(CREATE_TRIGGER_T_TBL_NOTE_B_I);
        AutoInsert(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE tbl_notes");
        onCreate(db);
    }

    public static String getNoteBody(SQLiteDatabase db, int id){
        Cursor cursor = db.query(NOTES_TABLE_NAME, new String[] {BODY_COLUMN_NAME}, "_id = ?", new String[] {id+""}, null, null, null );
        if(cursor !=null && cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(BODY_COLUMN_NAME));

        }
        else return "";
    }

    public static void deleteNote(SQLiteDatabase db, int id){
        db.beginTransaction();
        db.execSQL("DELETE FROM " + NOTES_TABLE_NAME +" WHERE " + ID_COLUMN_NAME + "=" +id+";" );
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public static void insertNote(SQLiteDatabase db, int id, String note){
        db.beginTransaction();
        if(id==-1) {
            db.execSQL("INSERT INTO  " + NOTES_TABLE_NAME + "(" + BODY_COLUMN_NAME + ") VALUES('" +
                    note + "');");
        }
        else db.execSQL("UPDATE "+ NOTES_TABLE_NAME +" SET " + BODY_COLUMN_NAME +"= '" + note + "' WHERE " + ID_COLUMN_NAME + "=" +id+";");
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void AutoInsert(SQLiteDatabase db){

        db.beginTransaction();
        try {
            for (int i = 1; i <10; i++) {
                ContentValues cv = new ContentValues();
                cv.put(BODY_COLUMN_NAME, "ZAM" + i);
                //cv.put(dbHelper.ID_COLUMN_NAME, i+1);
                cv.put(SUBJECT_COLUMN_NAME, "ZAM");
                cv.put(DATE_COLUMN_NAME, "160117");
                db.insert(NOTES_TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e){
        }
        finally {
            db.endTransaction();
        }

    }

}
