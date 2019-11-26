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
import com.uottawa.clinigo.model.Booking;
import com.uottawa.clinigo.model.ClinicBookings;
import com.uottawa.clinigo.model.Employee;
import com.uottawa.clinigo.model.WorkingHours;
import com.uottawa.clinigo.utilities.ValidationUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    String clinicId, patientId;
    private ClinicBookings clinicsBookings;
    private Employee employee;
    private TextView clinicName, clinicAddress, clinicRating;
    private FirebaseDatabase mDatabase;
    private DatabaseReference clinicReference, clinicBookingsReference;
    private DatabaseReference patientReference;
    private WorkingHours workingHours;
    private boolean patientHasBooking;

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
        clinicBookingsReference = mDatabase.getReference().child("users").child(clinicId).child("bookings");
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

        clinicBookingsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, ArrayList<Booking>> tempMap = new HashMap<>();
                if(dataSnapshot.getValue() != null){
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        ArrayList<Booking> tempArr = new ArrayList<>();
                        for(DataSnapshot data2: data.getChildren()){
                            Booking tempBooking = data2.getValue(Booking.class);
                            tempArr.add(tempBooking);
                        }
                        tempMap.put(data.getKey(), tempArr);
                    }
                    clinicsBookings = new ClinicBookings(tempMap);
                }
                else{
                    clinicsBookings = new ClinicBookings();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        patientReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.getKey().equals("hasBooking")){
                        if(data.getValue().toString().equals("true")){
                            patientHasBooking = true;
                        }else{
                            patientHasBooking = false;
                        }
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
        if(!workingHours.isOperational(mappingOfDay)){Toast.makeText(getApplicationContext(), "This Clinic is closed on "+dayOfWeek, Toast.LENGTH_LONG).show();}
        else{
            if(patientHasBooking){
                Toast.makeText(getApplicationContext(), "You Already have a Booking!", Toast.LENGTH_LONG).show();
            }
            else {
                Booking newBooking = new Booking(date, patientId);
                DatabaseReference bookingsReference = clinicReference.child("bookings");
                clinicsBookings.addBooking(newBooking);
                bookingsReference.child(newBooking.getDate()).setValue(clinicsBookings.getBookingsByDate(newBooking.getDate()));
                setPatientHasBooking(true);
            }
        }
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new SelectDateFragement();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }
    public void setPatientHasBooking(boolean hasBooking){
        patientReference.child("hasBooking").setValue(hasBooking);
    }
}
