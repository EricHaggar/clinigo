package com.uottawa.clinigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.uottawa.clinigo.R;

public class ClinicInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String userId;
    private String selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_info);

        userId = getIntent().getStringExtra("userId");

        Spinner spinner = findViewById(R.id.spinner_country);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);
    }

    //Method to go to Employee manage Working hours/services.. page
    public void goToEmployeePageOnClick(View view) {
        Intent intent = new Intent(this, EmployeeMainActivity.class);

        //Pass the user to the next Activity
        intent.putExtra("userId",userId);

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String country = parent.getItemAtPosition(position).toString();
        this.selectedCountry = country;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
