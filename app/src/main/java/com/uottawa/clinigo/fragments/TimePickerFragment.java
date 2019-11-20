package com.uottawa.clinigo.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.uottawa.clinigo.R;
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

            int hours, minutes;
            String[] time;
            Button timeButton;
            timeButton = workHours.isStartTimeSelected ? workHours.updateStartTimeButton : workHours.updateEndTimeButton;

            if (timeButton.getText().toString().equals(getString(R.string.clinic_closed))) {
                final Calendar c = Calendar.getInstance();
                hours = c.get(Calendar.HOUR_OF_DAY);
                minutes = c.get(Calendar.MINUTE);
            } else {
                time = timeButton.getText().toString().split(":");
                hours = Integer.parseInt(time[0]);
                minutes = Integer.parseInt(time[1]);
            }

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hours, minutes,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hours, int minutes) {
        this.workHours.updateTextViewFromTimePicker(hours, minutes);
    }
}