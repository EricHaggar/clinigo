package com.uottawa.clinigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.uottawa.clinigo.R;

public class EmployeePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        Intent intent = getIntent();
        String message = intent.getStringExtra("welcomeMessage");

        TextView welcomeMessage = findViewById(R.id.text_welcome_message);
        welcomeMessage.setText(message);
    }

    public void manageWorkingHoursOnClick(View view) {
        Intent intent = new Intent(this, EmployeeWorkingHoursActivity.class);
        startActivity(intent);
    }

    public void manageEmployeeServiceOnClick(View view) {
        Intent intent = new Intent(this, EmployeeServicesActivity.class);
        startActivity(intent);
    }
}
