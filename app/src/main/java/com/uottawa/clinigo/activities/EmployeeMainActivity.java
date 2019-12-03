package com.uottawa.clinigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uottawa.clinigo.R;

public class EmployeeMainActivity extends AppCompatActivity {

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

        Intent intent = getIntent();
        String message = intent.getStringExtra("welcomeMessage");

        TextView welcomeMessage = findViewById(R.id.text_welcome_message);
        welcomeMessage.setText(message);


        userId = intent.getStringExtra("userId");


    }

    public void manageWorkingHoursOnClick(View view) {
        Intent intent = new Intent(this, WorkingHoursActivity.class);

        //Pass the user to the next Activity
        intent.putExtra("userId", userId);

        startActivity(intent);
    }

    public void manageEmployeeServiceOnClick(View view) {
        Intent intent = new Intent(this, EmployeeServicesActivity.class);

        //Pass the user to the next Activity
        intent.putExtra("userId", userId);

        startActivity(intent);
    }

    public void manageProfileInformation(View view) {

        Intent intent = new Intent(this, EditClinicInfoActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}
