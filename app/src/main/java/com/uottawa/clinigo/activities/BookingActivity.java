package com.uottawa.clinigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.uottawa.clinigo.R;

public class BookingActivity extends AppCompatActivity {

    String clinicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        clinicId = getIntent().getStringExtra("userId");

    }
}
