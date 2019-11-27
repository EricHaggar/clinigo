package com.uottawa.clinigo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.adapters.PatientBookingsAdapter;
import com.uottawa.clinigo.model.Booking;

import java.util.ArrayList;

public class PatientBookingActivity extends AppCompatActivity {

    String patientId;
    private FirebaseDatabase mDatabase;
    private DatabaseReference patientReference;
    private ArrayList<Booking> patientArrayOfBookings;
    private TextView patientHasNoBookings;
    ListView bookingsListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_booking);
        patientId = getIntent().getStringExtra("patientId");
        initVariables();
    }

    public void initVariables(){
        mDatabase = FirebaseDatabase.getInstance();
        patientReference = mDatabase.getReference().child("users").child(patientId);
        patientHasNoBookings = findViewById(R.id.textView_patient_noBookings);
        bookingsListView = findViewById(R.id.listView_patient_bookings);

        patientReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.getKey().equals("bookings")){
                        ArrayList<Booking> patientsTempBookings = new ArrayList<>();
                        for(DataSnapshot patientBookings: data.getChildren() ){
                            Booking patientApp = patientBookings.getValue(Booking.class);
                            patientsTempBookings.add(patientApp);
                        }
                        patientArrayOfBookings = patientsTempBookings;
                        displayResults(patientArrayOfBookings);
                    }
                }
                if(patientArrayOfBookings == null){
                    patientHasNoBookings.setText("You currently have no Bookings up coming");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void displayResults(ArrayList<Booking> results){
        final PatientBookingsAdapter patientBookingsAdapter = new PatientBookingsAdapter(PatientBookingActivity.this, results);
        bookingsListView.setAdapter(patientBookingsAdapter);
        bookingsListView.setClickable(true);
    }
}
