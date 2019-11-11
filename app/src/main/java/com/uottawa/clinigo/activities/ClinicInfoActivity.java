package com.uottawa.clinigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.uottawa.clinigo.R;

public class ClinicInfoActivity extends AppCompatActivity {

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_info);

        userId = getIntent().getStringExtra("userId");
    }

    //Method to go to Employee manage Working hours/services.. page
    public void goToEmployeePageOnClick(View view) {
        Intent intent = new Intent(this, EmployeePage.class);

        //Pass the user to the next Activity
        intent.putExtra("userId",userId);

        startActivity(intent);
    }
}
