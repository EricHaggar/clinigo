package com.uottawa.camelclinic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.uottawa.camelclinic.R;

public class CreatingNewAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_new_account);

        //Putting the content of the spinner
        String[] arraySpinner = new String[] {
                "Employee", "Patient"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    public void goToWelcomeNewUser (View view) {
        EditText firstNameEditText;
        EditText lastNameEditText;
        EditText passwordEditText;
        EditText confirmPassEditText;
        EditText role;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

        Intent intent = new Intent(this, NewAccountWelcome.class);

        startActivity(intent);
    }
}
