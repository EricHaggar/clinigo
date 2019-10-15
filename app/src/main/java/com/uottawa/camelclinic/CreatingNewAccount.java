package com.uottawa.camelclinic;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;

public class CreatingNewAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_new_account);
    }

    public void goToWelcomeNewUser (View view) {
        Intent intent = new Intent(this, NewAccountWelcome.class);

        startActivity(intent);
    }
}
