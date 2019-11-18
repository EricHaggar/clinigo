package com.uottawa.clinigo.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.uottawa.clinigo.activities.WorkingHoursActivity;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    WorkingHoursActivity workHours;

    public TimePickerFragment(WorkingHoursActivity workHours) {
        this.workHours = workHours;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

//        TextView startTime = (TextView) getView().findViewById(R.id.startTime);
////        // Do something with the time chosen by the user
////        TextView tv1 = (TextView)findViewById(R.id.startTime);
//        System.out.println(startTime);
//        startTime.setText("Hello");
////        setContentView(tv1);
        this.workHours.changeTime(hourOfDay, minute);
    }
}