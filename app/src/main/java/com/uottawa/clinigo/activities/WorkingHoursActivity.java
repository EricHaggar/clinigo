package com.uottawa.clinigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.fragments.TimePickerFragment;
import com.uottawa.clinigo.model.WorkingHours;

import java.util.ArrayList;

public class WorkingHoursActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseReference usersReference;
    DatabaseReference workingHoursReference;
    String userId;
    TextView[] startTimeTextViews;
    TextView[] endTimeTextViews;
    TextView updateStartTimeTextView;
    TextView updateEndTimeTextView;
    Switch closedSwitch;
    Spinner spinner;
    private String selectedDay;
    private boolean isStartTimeSelected;
    private WorkingHours workingHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        // should be moved to adapters later.
        spinner = findViewById(R.id.spinner_days_of_week);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.daysOfTheWeek, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);
        int default_position = spinner_adapter.getPosition("Monday");
        spinner.setSelection(default_position);

        usersReference = FirebaseDatabase.getInstance().getReference("users");
        workingHoursReference = usersReference.child(userId).child("workingHours");

        startTimeTextViews = new TextView[]{findViewById(R.id.text_monday_start), findViewById(R.id.text_tuesday_start), findViewById(R.id.text_wednesday_start), findViewById(R.id.text_thursday_start), findViewById(R.id.text_friday_start), findViewById(R.id.text_saturday_start), findViewById(R.id.text_sunday_start)};
        endTimeTextViews = new TextView[]{findViewById(R.id.text_monday_end), findViewById(R.id.text_tuesday_end), findViewById(R.id.text_wednesday_end), findViewById(R.id.text_thursday_end), findViewById(R.id.text_friday_end), findViewById(R.id.text_saturday_end), findViewById(R.id.text_sunday_end)};
        updateStartTimeTextView = findViewById(R.id.text_update_start_time);
        updateEndTimeTextView = findViewById(R.id.text_update_end_time);
        closedSwitch = findViewById(R.id.switch_closed);
    }

    @Override
    protected void onStart() {
        super.onStart();

        workingHoursReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    workingHours = dataSnapshot.getValue(WorkingHours.class);
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

        if (!closedSwitch.isChecked()) {
            DialogFragment newFragment = new TimePickerFragment(this);
            newFragment.show(getSupportFragmentManager(), "timePicker");

            if (v.getId() == R.id.text_update_start_time)
                isStartTimeSelected = true;
            else
                isStartTimeSelected = false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String day = parent.getItemAtPosition(position).toString();
        selectedDay = day;
        updateTextViewsFromWidget();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

    public void updateTimeOnClick(View view) {

        if (validateUpdate()) {

            ArrayList<String> newStartTime = workingHours.getStartTime();
            ArrayList<String> newEndTime = workingHours.getEndTime();

            newStartTime.set(getIndexForSelectedDay(selectedDay), updateStartTimeTextView.getText().toString());
            newEndTime.set(getIndexForSelectedDay(selectedDay), updateEndTimeTextView.getText().toString());

            workingHours.setStartTime(newStartTime);
            workingHours.setEndTime(newEndTime);

            pushToFirebase(workingHours);

            Toast.makeText(getApplicationContext(), "Working Hours updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTextViewFromTimePicker(int hours, int minutes) {

        TextView textChange;

        if (isStartTimeSelected)
            textChange = updateStartTimeTextView;
        else
            textChange = updateEndTimeTextView;

        textChange.setText(String.format("%02d:%02d", hours, minutes));
    }

    public void updateTextViewsFromWidget() {

        if (!closedSwitch.isChecked()) {
            int index = getIndexForSelectedDay(selectedDay);

            String startTime = workingHours.getStartTime().get(index);
            String endTime = workingHours.getEndTime().get(index);

            updateStartTimeTextView.setText(startTime);
            updateEndTimeTextView.setText(endTime);
        }
    }

    private void populateTimeInTable(WorkingHours workingHours) {
        for (int i = 0; i < 7; i++) {
            startTimeTextViews[i].setText(workingHours.getStartTime().get(i));
            endTimeTextViews[i].setText(workingHours.getEndTime().get(i));
        }
    }

    public void handleSwitchOnClick(View view) {

        if (closedSwitch.isChecked()) {
            updateStartTimeTextView.setText("--");
            updateEndTimeTextView.setText("--");
            updateStartTimeTextView.setEnabled(false);
            updateEndTimeTextView.setEnabled(false);
        } else {
            updateStartTimeTextView.setEnabled(true);
            updateEndTimeTextView.setEnabled(true);
        }

        updateTextViewsFromWidget();
    }

    public boolean validateUpdate() {

        boolean valid = true;

        String startTime = updateStartTimeTextView.getText().toString();
        String endTime = updateEndTimeTextView.getText().toString();

        if (closedSwitch.isChecked()) {
            if (!startTime.equals("--") && !endTime.equals("--")) {
                valid = false;
            }
        } else if (startTime.equals("--") || endTime.equals("--")) {
            Toast.makeText(getApplicationContext(), "Both start time and end time need to be filled!", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            valid = validTimeRange(startTime, endTime);
        }
        return valid;
    }

    public boolean validTimeRange(String startTime, String endTime) {

        int convertedStartTime = convertToMinutes(startTime);
        int convertedEndTime = convertToMinutes(endTime);
        int difference = convertedEndTime - convertedStartTime;

        if (difference < 0) {
            Toast.makeText(getApplicationContext(), "Invalid time range.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (difference < 30) {
            Toast.makeText(getApplicationContext(), "Time range is too short (Minimum: 30 minutes).", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public int convertToMinutes(String time) {
        String[] hourMin = time.split(":");
        int hours = Integer.parseInt(hourMin[0]);
        int minutes = Integer.parseInt(hourMin[1]);
        int hoursInMinutes = hours * 60;
        return hoursInMinutes + minutes;
    }
}

