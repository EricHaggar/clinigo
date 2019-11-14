package com.uottawa.clinigo.activities;

import com.uottawa.clinigo.utilities.ValidationUtilities;

import com.uottawa.clinigo.R;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.os.Bundle;

import com.uottawa.clinigo.R;
import androidx.fragment.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import java.util.Calendar;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.view.View;

public class WorkingHoursActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);

        // should be moved to adapters later.
        Spinner spinner = findViewById(R.id.spinner_daysOfTheWeek);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.daysOfTheWeek, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);
        int default_position = spinner_adapter.getPosition("Monday");
        spinner.setSelection(default_position);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String day = parent.getItemAtPosition(position).toString();
        this.selectedDay = day;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
