package com.uottawa.clinigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.uottawa.clinigo.R;


public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
    }

    public void manageAccountsOnClick(View view) {
        Intent intent = new Intent(this, AdminUsersActivity.class);
        startActivity(intent);
    }

    public void manageServicesOnClick(View view) {
        Intent intent = new Intent(this, AdminServicesActivity.class);
        startActivity(intent);
    }
}

