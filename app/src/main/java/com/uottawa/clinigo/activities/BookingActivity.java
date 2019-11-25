package com.uottawa.clinigo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.fragments.SelectDateFragement;
import com.uottawa.clinigo.model.Employee;
import com.uottawa.clinigo.model.WorkingHours;
import com.uottawa.clinigo.utilities.ValidationUtilities;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {

    String clinicId, patientId;
    private Employee employee;
    private TextView clinicName, clinicAddress, clinicRating;
    private FirebaseDatabase mDatabase;
    private DatabaseReference clinicReference;
    private DatabaseReference patientReference;
    private WorkingHours workingHours;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent intent = getIntent();
        clinicId = intent.getStringExtra("userId");
        patientId = getIntent().getStringExtra("patientId");
        initVariables();

        final SelectDateFragement newFragement = new SelectDateFragement();
    }

    public void initVariables(){

        mDatabase = FirebaseDatabase.getInstance();
        clinicReference = mDatabase.getReference().child("users").child(clinicId);
        patientReference = mDatabase.getReference().child("users").child(patientId);
        clinicName = findViewById(R.id.textView_clinic_name);
        clinicAddress = findViewById(R.id.textView_clinic_address);


        clinicReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.getKey().equals("clinicInfo")){
                        clinicName.setText(data.child("name").getValue().toString());
                        clinicAddress.setText(data.child("location").child("address").getValue().toString()+", "+
                                data.child("location").child("province").getValue().toString());
                    }
                    if(data.getKey().equals("workingHours")){
                        WorkingHours temp = data.getValue(WorkingHours.class);
                        workingHours = temp;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void book(String date, String dayOfWeek){
        int mappingOfDay = ValidationUtilities.mapDayOfWeekToInt(dayOfWeek);
        if(workingHours.getStartTimes().get(mappingOfDay).equals("--")){Toast.makeText(getApplicationContext(), "This clinic is closed on "+dayOfWeek, Toast.LENGTH_LONG).show();}
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new SelectDateFragement();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }
}
