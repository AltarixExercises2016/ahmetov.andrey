package com.example.andrey092009.notepad;

/**
 * Created by 1 on 23.01.2017.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;

public class DialogDelete extends DialogFragment implements OnClickListener {

    final String LOG_TAG = "myLogs";
    protected boolean delNoteCheck = false;
    int id_note=-1;


    public boolean getDelNoteCheck(){
        return delNoteCheck;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle("Title!").setPositiveButton(R.string.yes, this)
                .setNegativeButton(R.string.no, this)
                .setMessage(R.string.message_text)
                ;
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        int i = 0;
        id_note=getArguments().getInt("id_note");
        switch (which) {
            case Dialog.BUTTON_POSITIVE:{
                i = R.string.yes;
                DBHelper.deleteNote(new DBHelper(getActivity()).getWritableDatabase(), id_note);
                getActivity().finish();
                break;}
            case Dialog.BUTTON_NEGATIVE:
                i = R.string.no;
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}