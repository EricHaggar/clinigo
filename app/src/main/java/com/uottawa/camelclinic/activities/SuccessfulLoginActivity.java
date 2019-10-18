package com.uottawa.camelclinic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.uottawa.camelclinic.R;

public class SuccessfulLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_login);

        Intent intent = getIntent();
        String message = intent.getStringExtra("welcomeMessage");

        TextView welcomeMessage =  findViewById(R.id.welcomeMessage);
        welcomeMessage.setText(message);
    }
}
