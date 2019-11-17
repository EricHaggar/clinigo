package com.uottawa.clinigo.activities;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.adapters.ServiceAdapter;
import com.uottawa.clinigo.model.Service;
import com.uottawa.clinigo.model.WorkingHours;
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

import java.util.ArrayList;
import java.util.Calendar;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.view.View;
import android.widget.TextView;

public class WorkingHoursActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String selectedDay;
    private boolean isStartTimeSelected;
    private WorkingHours workingHours;
    DatabaseReference usersReference;
    DatabaseReference workingHoursReference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        // should be moved to adapters later.
        Spinner spinner = findViewById(R.id.spinner_daysOfTheWeek);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.daysOfTheWeek, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);
        int default_position = spinner_adapter.getPosition("Monday");
        spinner.setSelection(default_position);

        usersReference = FirebaseDatabase.getInstance().getReference("users");
        workingHoursReference = usersReference.child(userId).child("workingHours");


    }

    @Override
    protected void onStart() {
        super.onStart();

        workingHoursReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    workingHours=dataSnapshot.getValue(WorkingHours.class);
                    populateTimeInTable(workingHours);
                } else {//Setting a default time if nothing has been changed yet
                    workingHours = new WorkingHours();
                    populateTimeInTable(workingHours);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment(this);
        newFragment.show(getSupportFragmentManager(), "timePicker");

        if (v.getId() == R.id.startTime)
            isStartTimeSelected = true;
        else
            isStartTimeSelected = false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String day = parent.getItemAtPosition(position).toString();
        this.selectedDay = day;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private boolean isTimeInAm(int hours) {
        if (hours <= 12)
            return true;
        else
            return false;
    }
    private  int convertHourToAmPmFormat(int hours) {
        if(isTimeInAm(hours))
            return hours;
        else
            return (hours-12);
    }

    private void pushToFirebase(WorkingHours wh) {
        DatabaseReference userRef = usersReference.child(userId).child("workingHours");
        userRef.setValue(wh);
    }

    private int getIndexForSelectedDay(String day) {

        if (day.equals("Monday"))
            return 0;
        else if (day.equals("Tuesday"))
            return 1;
        else if (day.equals("Wednesday"))
            return 2;
        else if (day.equals("Thursday"))
            return 3;
        else if (day.equals("Friday"))
            return 4;
        else if (day.equals("Saturday"))
            return 5;
        else if (day.equals("Sunday"))
            return 6;
        else
            return 0;
    }

    public void updateTime(View view) {
        TextView startTime = (TextView)findViewById(R.id.startTime);
        TextView endTime = (TextView)findViewById(R.id.endTime);

        ArrayList<String> newStartTime = workingHours.getStartTime();
        ArrayList<String> newEndTime = workingHours.getEndTime();

        newStartTime.set(getIndexForSelectedDay(this.selectedDay),startTime.getText().toString());
        newEndTime.set(getIndexForSelectedDay(this.selectedDay),endTime.getText().toString());

        workingHours.setStartTime(newStartTime);
        workingHours.setEndTime(newEndTime);

        pushToFirebase(workingHours);
    }

    private void populateTimeInTable(WorkingHours workingHours) {
        //Text Views
        TextView []startTime = {findViewById(R.id.mondayStart),findViewById(R.id.tuesdayStart),findViewById(R.id.wednesdayStart),findViewById(R.id.thursdayStart),findViewById(R.id.fridayStart),findViewById(R.id.saturdayStart),findViewById(R.id.sundayStart)};

        TextView[] endTime = { findViewById(R.id.mondayEnd),findViewById(R.id.tuesdayEnd), findViewById(R.id.wednesdayEnd), findViewById(R.id.thursdayEnd), findViewById(R.id.fridayEnd), findViewById(R.id.saturdayEnd),findViewById(R.id.sundayEnd)};


        for (int i=0; i<7; i++) {
            startTime[i].setText(workingHours.getStartTime().get(i));
            endTime[i].setText(workingHours.getEndTime().get(i));
        }
    }

    public void changeTime (int hours, int minutes) {
        TextView textChange;
        if (isStartTimeSelected)
            textChange = (TextView)findViewById(R.id.startTime);
        else
            textChange = (TextView)findViewById(R.id.endTime);

        String morningNightFormat;

        if (isTimeInAm(hours))
            morningNightFormat = "AM";
        else
            morningNightFormat = "PM";

        String minutesString;
        minutesString = Integer.toString(minutes);

        if (minutes < 10)
            minutesString = "0" + minutesString;


        textChange.setText(convertHourToAmPmFormat(hours) + ":" + minutesString + " " + morningNightFormat);
    }
}


