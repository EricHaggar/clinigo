package com.uottawa.clinigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.uottawa.clinigo.R;

public class SuccessfulLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_login);

        Intent intent = getIntent();
        String message = intent.getStringExtra("welcomeMessage");

        TextView welcomeMessage = findViewById(R.id.text_welcome_message);
        welcomeMessage.setText(message);
    }
}
