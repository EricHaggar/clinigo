package com.uottawa.clinigo.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uottawa.clinigo.R;

public class BookingActivity extends AppCompatActivity {

    String clinicId, patientId;
    private TextView clinicName;
    private FirebaseDatabase mDatabase;
    private DatabaseReference clinicReference;
    private DatabaseReference patientReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent intent = getIntent();
        clinicId = intent.getStringExtra("userId");
        patientId = getIntent().getStringExtra("patientId");
        initVariables();

    }

    public void initVariables(){

        mDatabase = FirebaseDatabase.getInstance();
        clinicReference = mDatabase.getReference().child("users").child(clinicId);
        patientReference = mDatabase.getReference().child("users").child(patientId);
        clinicName = findViewById(R.id.textView_clinic_name);
        clinicName.setText("Testing");

    }
}
