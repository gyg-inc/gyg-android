package com.capstonegyg.gyg.UI.PostGyg;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.app.DialogFragment;
import android.widget.TextView;
import java.util.Date;

import com.capstonegyg.gyg.R;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment{

    int year;
    int month;
    int day;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), (PostGygActivity)getActivity(), year, month, day);
    }

    /*public void onDateSet(DatePicker view, int year, int month, int day) {

        this.year = year;
        this.month = month;
        this.day = day;

        TextView V = view.findViewById(R.id.display_date);
        (TextView)view.findViewById(R.id.display_date).setText("Hi");


    }*/

   /* public Date getDateFromDatePicker(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }*/
}
