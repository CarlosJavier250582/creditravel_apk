package com.example.carlosje.creditravel;

/**
 * Created by carlosje on 8/15/2018.
 */


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import java.util.Calendar;

public class DatePick extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private int mNum;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mNum = getArguments().getInt("num");
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        return new DatePickerDialog(getActivity(), (CapturaActivity)getActivity(),  year, month, day);






    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    }

}