package com.uottawa.clinigo.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.fragments.SelectDateFragement;
import com.uottawa.clinigo.model.Booking;
import com.uottawa.clinigo.model.ClinicBookings;
import com.uottawa.clinigo.model.ClinicInfo;
import com.uottawa.clinigo.model.WorkingHours;
import com.uottawa.clinigo.utilities.ValidationUtilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    String clinicId, patientId;
    private ClinicBookings clinicsBookings;
    private TextView clinicName, clinicAddress, clinicCheckInWaitTime;
    private FirebaseDatabase mDatabase;
    private DatabaseReference clinicReference, clinicBookingsReference;
    private DatabaseReference patientReference;
    private WorkingHours workingHours;
    private int checkInWaitTime;
    private boolean clinicOpenToday;
    private String currentDate;
    private ArrayList<Booking> patientArrayOfBookings;
    private RatingBar ratingBar;
    private TextView mondayHours, tuesdayHours, wednesdayHours, thursdayHours, fridayHours, saturdayHours, sundayHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Intent intent = getIntent();
        clinicId = intent.getStringExtra("userId");
        patientId = getIntent().getStringExtra("patientId");
        initVariables();
    }

    @Override
    protected void onStart() {
        super.onStart();

        clinicReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("clinicInfo")) {
                    ClinicInfo clinicInfo = dataSnapshot.child("clinicInfo").getValue(ClinicInfo.class);
                    ratingBar.setRating(clinicInfo.calculateAverageRating());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void initVariables() {

        mDatabase = FirebaseDatabase.getInstance();
        clinicReference = mDatabase.getReference().child("users").child(clinicId);
        clinicBookingsReference = mDatabase.getReference().child("users").child(clinicId).child("bookings");
        patientReference = mDatabase.getReference().child("users").child(patientId);
        clinicName = findViewById(R.id.textView_clinic_name);
        clinicAddress = findViewById(R.id.textView_clinic_address);
        clinicCheckInWaitTime = findViewById(R.id.textView_checkIn_waitTime);
        currentDate = getCurrentDate();
        ratingBar = findViewById(R.id.rating);
        mondayHours = findViewById(R.id.text_monday_hours);
        tuesdayHours = findViewById(R.id.text_tuesday_hours);
        wednesdayHours = findViewById(R.id.text_wednesday_hours);
        thursdayHours = findViewById(R.id.text_thursday_hours);
        fridayHours = findViewById(R.id.text_friday_hours);
        saturdayHours = findViewById(R.id.text_saturday_hours);
        sundayHours = findViewById(R.id.text_sunday_hours);

        clinicReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals("clinicInfo")) {
                        ClinicInfo clinicInfo = data.getValue(ClinicInfo.class);
                        clinicName.setText(clinicInfo.getName());
                        clinicAddress.setText(clinicInfo.getLocation().getAddress() + ", " + clinicInfo.getLocation().getProvince());
                        ratingBar.setRating(clinicInfo.calculateAverageRating());
                    }
                    if (data.getKey().equals("workingHours")) {
                        WorkingHours temp = data.getValue(WorkingHours.class);
                        workingHours = temp;

                        if (workingHours != null) {
                            for (int i = 0; i < workingHours.getStartTimes().size(); i++) {
                                if (workingHours.isOperational(i)) {
                                    String startTime = workingHours.getStartTimes().get(i);
                                    String endTime = workingHours.getEndTimes().get(i);

                                    getTextViewFromIndex(i).setText(" " + startTime + " - " + endTime);
                                } else {
                                    getTextViewFromIndex(i).setText(" Closed");
                                }
                            }
                        }
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
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ArrayList<Booking> tempArr = new ArrayList<>();
                        for (DataSnapshot data2 : data.getChildren()) {
                            Booking tempBooking = data2.getValue(Booking.class);
                            tempArr.add(tempBooking);
                        }
                        tempMap.put(data.getKey(), tempArr);
                    }
                    clinicsBookings = new ClinicBookings(tempMap);
                    ArrayList<Booking> todayBookings = tempMap.get(currentDate);
                    if(todayBookings != null){
                        checkInWaitTime = todayBookings.size()*15;
                        clinicCheckInWaitTime.setText(" "+ checkInWaitTime + " min");
                    }
                    else{
                        int dayOfWeek = ValidationUtilities.mapDayOfWeekToInt(getDayOfWeek(null));
                        if(workingHours.isOperational(dayOfWeek)){
                            clinicCheckInWaitTime.setText(" 0 min");
                        }else{
                            clinicCheckInWaitTime.setText(" N/A");
                        }
                    }
                } else {
                    clinicCheckInWaitTime.setText(" 0 min");
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
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals("bookings")) {
                        ArrayList<Booking> patientsTempBookings = new ArrayList<>();
                        for (DataSnapshot patientBookings : data.getChildren()) {
                            Booking patientApp = patientBookings.getValue(Booking.class);
                            patientsTempBookings.add(patientApp);
                        }
                        patientArrayOfBookings = patientsTempBookings;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void book(String date, String dayOfWeek) {
        int mappingOfDay = ValidationUtilities.mapDayOfWeekToInt(dayOfWeek);
        if (!workingHours.isOperational(mappingOfDay)) {
            Toast.makeText(getApplicationContext(), "This clinic is closed on " + dayOfWeek, Toast.LENGTH_LONG).show();
        } else {
            if (clinicsBookings.patientHasBookingOnDate(date, patientId)) {

                Toast.makeText(getApplicationContext(), "You already have a booking on that date!", Toast.LENGTH_LONG).show();
            } else {
                if (patientArrayOfBookings == null) {
                    patientArrayOfBookings = new ArrayList<>();
                }
                Booking newBooking = new Booking(date, patientId);
                String clinicNamestr = clinicName.getText().toString().trim();
                String clinicAddressStr = clinicAddress.getText().toString().trim();
                Booking patientBooking = new Booking(date, clinicNamestr, clinicAddressStr, clinicId);
                DatabaseReference bookingsReference = clinicReference.child("bookings");
                clinicsBookings.addBooking(newBooking);
                patientArrayOfBookings.add(patientBooking);
                bookingsReference.child(newBooking.getDate()).setValue(clinicsBookings.getBookingsByDate(newBooking.getDate()));
                setPatientArrayOfBookings(patientArrayOfBookings);
                if (date.equals(currentDate)) {
                    Toast.makeText(getApplicationContext(), "You are checked-in for today!", Toast.LENGTH_LONG).show();
                }
                getPatientBookingsActivity(null);
            }
        }
    }

    public void checkIn(View view) {
        String day = getDayOfWeek(view);
        book(currentDate, day);
    }

    public void showDatePicker(View v) {
        DialogFragment newFragment = new SelectDateFragement();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }

    public void setPatientArrayOfBookings(ArrayList<Booking> bookings) {
        patientReference.child("bookings").setValue(bookings);
    }

    public int getCheckinWaitTime() {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendarObj = Calendar.getInstance();
        String currentDate = df.format(calendarObj.getTime());
        if (clinicsBookings.getBookingsByDate(currentDate) != null) {
            return clinicsBookings.getWaitingTime(currentDate);
        }
        return 0;
    }

    public String getCurrentDate() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendarObj = Calendar.getInstance();
        String currentDate = df.format(calendarObj.getTime());
        return currentDate;
    }

    public int calculateWaitTime(int size) {
        return size * 15;
    }

    public void getPatientBookingsActivity(View view) {
        Intent intent = new Intent(getApplicationContext(), PatientBookingActivity.class);
        intent.putExtra("patientId", patientId);
        intent.putExtra("clinicId", clinicId);
        startActivity(intent);
        finish();
    }

    public String getDayOfWeek(View view) {
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
        String dayOfWeek = simpleDateformat.format(now);
        return dayOfWeek;
    }

    public void rateOnClick(View view) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rate_clinic_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button submitButton = dialogView.findViewById(R.id.button_submit);
        final Button cancelButton = dialogView.findViewById(R.id.button_cancel);
        final RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);


        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final float rating = ratingBar.getRating();
                if (rating == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a rating!", Toast.LENGTH_SHORT).show();
                } else {
                    addRating(rating);
                    alertDialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    public void addRating(float rating) {
        DatabaseReference dR = clinicReference.child("clinicInfo");
        final float currentRating = rating;

        dR.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numberOfRatings = Integer.parseInt(dataSnapshot.child("numberOfRatings").getValue().toString()) + 1;
                double sumOfRatings = Float.parseFloat(dataSnapshot.child("sumOfRatings").getValue().toString()) + currentRating;
                dataSnapshot.child("numberOfRatings").getRef().setValue(numberOfRatings);
                dataSnapshot.child("sumOfRatings").getRef().setValue(sumOfRatings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toast.makeText(getApplicationContext(), "Rating Submitted!", Toast.LENGTH_SHORT).show();
    }

    public TextView getTextViewFromIndex(int index) {

        if (index == 0)
            return mondayHours;
        else if (index == 1)
            return tuesdayHours;
        else if (index == 2)
            return wednesdayHours;
        else if (index == 3)
            return thursdayHours;
        else if (index == 4)
            return fridayHours;
        else if (index == 5)
            return saturdayHours;
        else if (index == 6)
            return sundayHours;
        else
            return null;
    }
}
