package com.uottawa.clinigo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.model.Address;
import com.uottawa.clinigo.model.Employee;
import com.uottawa.clinigo.model.ClinicInfo;


import com.uottawa.clinigo.utilities.ValidationUtilities;

import com.uottawa.clinigo.R;

public class ClinicInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String userId;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersReference;
    private String selectedCountry;
    private EditText postalCodeEditText;
    private EditText clinicNameEditText;
    private EditText phoneNumberEditText;
    private EditText descriptionEditText;
    private EditText streetNameEditText;
    private EditText cityEditText;
    private EditText provinceEditText;
    private CheckBox licensedCheckbox;
    private ClinicInfo clinicInfo;
    private Employee employee;
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_info);
        initVariables();

        userId = getIntent().getStringExtra("userId");
        // should be moved to adapters later.
        Spinner spinner = findViewById(R.id.spinner_country);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);
        int default_position = spinner_adapter.getPosition("Canada");
        spinner.setSelection(default_position);

    }

    public void initVariables(){

        // Database entry point
        mDatabase = FirebaseDatabase.getInstance();
        usersReference = mDatabase.getReference("users");

        clinicNameEditText = findViewById(R.id.edit_clinic_name);
        phoneNumberEditText = findViewById(R.id.edit_clinic_phoneNumber);
        descriptionEditText = findViewById(R.id.edit_description);
        streetNameEditText = findViewById(R.id.edit_street_name);
        cityEditText = findViewById(R.id.edit_city);
        provinceEditText = findViewById(R.id.edit_province_name);
        postalCodeEditText = findViewById(R.id.edit_postal_code);
        licensedCheckbox = findViewById(R.id.checkbox_licensed);
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

    public void createProfileOnClick(View view){

        final String clinicName = clinicNameEditText.getText().toString().trim();
        final String phoneNumber = phoneNumberEditText.getText().toString().trim();
        final String description = descriptionEditText.getText().toString().trim();
        final String streetName = streetNameEditText.getText().toString().trim();
        final String city = cityEditText.getText().toString().trim();
        final String postalCode = postalCodeEditText.getText().toString().trim();
        final String province = provinceEditText.getText().toString();
        final String country = selectedCountry;
        final boolean licensed = licensedCheckbox.isChecked();

        DatabaseReference userRef = usersReference.child(userId);

        if(validProfileForm(clinicName, phoneNumber, description, streetName, city, postalCode, province)){

            address = new Address(streetName, city, postalCode, province, country);
            clinicInfo = new ClinicInfo(clinicName, phoneNumber,address, description, licensed);

            Toast.makeText(getApplicationContext(), "Valid form!", Toast.LENGTH_SHORT).show();
            userRef.setValue(clinicInfo);
        }

    }

    public boolean validProfileForm(String clinicName, String phoneNumber, String description,String streetName, String city, String postalCode, String province){

        if(TextUtils.isEmpty(clinicName)){
            clinicNameEditText.setError("Clinic Name cannot be empty.");
            return false;
        }
        if(clinicName.length() < 2){
            clinicNameEditText.setError("Invalid Clinic Name.");
        }
        if(TextUtils.isEmpty(phoneNumber)){
            postalCodeEditText.setError("Phone Number cannpt be empty.");
            return false;
        }
        if(!ValidationUtilities.isValidPhoneNumber(phoneNumber)){
            phoneNumberEditText.setError("Invalid Phone Number.");
            return false;
        }
        if(TextUtils.isEmpty(description)){
            descriptionEditText.setError("Please provide a simple description.");
            return false;
        }
        if(TextUtils.isEmpty(streetName)){
            streetNameEditText.setError("Street Name cannot be empty.");
            return false;
        }
        if(streetName.length() < 4){
            streetNameEditText.setError("Invalid Street Name.");
            return false;
        }
        if(TextUtils.isEmpty(city)){
            cityEditText.setError("City cannot be empty.");
            return false;
        }
        if(city.length() < 2){
            cityEditText.setError("Invalid City Name.");
            return false;
        }
        if(TextUtils.isEmpty(postalCode)){
            postalCodeEditText.setError("PostalCode cannot be empty.");
        }
        if(!ValidationUtilities.isValidPostalCode(postalCode)){
            postalCodeEditText.setError("Invalid PostalCode.");
            return false;
        }
        if(TextUtils.isEmpty(province)){
            provinceEditText.setError("Province cannot be empty.");
            return false;
        }
        if(province.length() < 2){
            provinceEditText.setError("Invalid Province.");
        }
        return true;

    }
}
