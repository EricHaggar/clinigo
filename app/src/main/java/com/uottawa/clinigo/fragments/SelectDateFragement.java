package com.uottawa.clinigo.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;


import androidx.fragment.app.DialogFragment;

import com.uottawa.clinigo.activities.BookingActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SelectDateFragement extends DialogFragment {

    public String datePicked;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }
    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                    Date date = new Date(year, month, day-1);
                    String dayOfWeek = simpledateformat.format(date);
                    datePicked = view.getDayOfMonth()+"-"+(view.getMonth()+1)+"-"+view.getYear();
                    boolean valid = true;
                    Calendar cal = Calendar.getInstance();
                    int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    int currentDay = Calendar.getInstance().get(Calendar.DATE);

                    if(view.getYear() < currentYear){valid = false;}
                    if(currentYear == view.getYear() && view.getMonth()+1 < currentMonth){valid = false;}
                    if(view.getMonth()+1 == currentMonth && currentDay > view.getDayOfMonth()){valid = false;}
                    if(!valid){Toast.makeText(getActivity(), "Invalid Date Selection.", Toast.LENGTH_LONG).show();}

                    else {
                        ((BookingActivity) getActivity()).book(datePicked, dayOfWeek);
                    }
                }
            };
}
