package com.uottawa.clinigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.uottawa.clinigo.R;

public class EmployeeServicesActivity extends AppCompatActivity {

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_services);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        System.out.println("AdelHI");
        System.out.println(userId);
    }


    public void goToAddNewServicesPageOnClick(View view) {
        Intent intent = new Intent(this, AvailableServicesActivity.class);

        //Pass the user to the next Activity
        intent.putExtra("userId",userId);

        startActivity(intent);
    }
}
