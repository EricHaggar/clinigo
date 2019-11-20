package com.uottawa.clinigo.activities;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.model.ClinicInfo;
import com.uottawa.clinigo.model.Location;
import com.uottawa.clinigo.utilities.ValidationUtilities;

public class ClinicInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String userId, userEmail, userFirstName, userLastName, userRole;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersReference;
    private String selectedCountry;
    private EditText postalCodeEditText;
    private EditText clinicNameEditText;
    private EditText phoneNumberEditText;
    private EditText descriptionEditText;
    private EditText addressEditText;
    private EditText cityEditText;
    private EditText provinceEditText;
    private CheckBox licensedCheckbox;
    private ClinicInfo clinicInfo;
    private Location location;

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

    public void initVariables() {

        // Database entry point
        mDatabase = FirebaseDatabase.getInstance();
        usersReference = mDatabase.getReference().child("users");

        clinicNameEditText = findViewById(R.id.edit_clinic_name);
        phoneNumberEditText = findViewById(R.id.edit_clinic_phoneNumber);
        descriptionEditText = findViewById(R.id.edit_description);
        addressEditText = findViewById(R.id.edit_address);
        cityEditText = findViewById(R.id.edit_city);
        provinceEditText = findViewById(R.id.edit_province_name);
        postalCodeEditText = findViewById(R.id.edit_postal_code);
        licensedCheckbox = findViewById(R.id.checkbox_licensed);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String country = parent.getItemAtPosition(position).toString();
        this.selectedCountry = country;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void createProfileOnClick(View view) {

        final String clinicName = clinicNameEditText.getText().toString().trim();
        final String phoneNumber = phoneNumberEditText.getText().toString().trim();
        final String description = descriptionEditText.getText().toString().trim();
        final String address = addressEditText.getText().toString().trim();
        final String city = cityEditText.getText().toString().trim();
        final String postalCode = postalCodeEditText.getText().toString().trim();
        final String province = provinceEditText.getText().toString();
        final String country = selectedCountry;
        final boolean licensed = licensedCheckbox.isChecked();

        final DatabaseReference userRef = usersReference.child(userId).child("clinicInfo");
        if (validProfileForm(clinicName, phoneNumber, description, address, city, postalCode, province)) {

            location = new Location(address, city, postalCode, province, country);
            clinicInfo = new ClinicInfo(clinicName, phoneNumber, location, description, licensed);
            try {
                userRef.setValue(clinicInfo);
                Intent intent = new Intent(getApplicationContext(), EmployeeMainActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error," + e, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public boolean validProfileForm(String clinicName, String phoneNumber, String description, String address, String city, String postalCode, String province) {

        boolean error = true;

        if (TextUtils.isEmpty(clinicName)) {
            clinicNameEditText.setError("Clinic Name cannot be empty.");
            error = false;
        } else if (clinicName.length() < 2) {
            clinicNameEditText.setError("Invalid Clinic Name. [Clinic name should be at least 2 characters]");
            error = false;
        } else if (!ValidationUtilities.isValidName(clinicName)) {
            clinicNameEditText.setError("Invalid Clinic Name.");
            error = false;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberEditText.setError("Phone Number cannot be empty.");
            error = false;
        } else if (!ValidationUtilities.isValidPhoneNumber(phoneNumber)) {
            phoneNumberEditText.setError("Invalid Phone Number.");
            error = false;
        }

        if (TextUtils.isEmpty(description)) {
            descriptionEditText.setError("Please provide a simple description.");
            error = false;
        }

        if (TextUtils.isEmpty(address)) {
            addressEditText.setError("Please provide an address.");
            error = false;
        } else if (!ValidationUtilities.isValidAddress(address)) {
            addressEditText.setError("Invalid address.");
            error = false;
        }

        if (TextUtils.isEmpty(city)) {
            cityEditText.setError("City cannot be empty.");
            error = false;
        } else if (city.length() < 2) {
            cityEditText.setError("Invalid City Name. [City should be at least 2 characters]");
            error = false;
        }

        if (TextUtils.isEmpty(postalCode)) {
            postalCodeEditText.setError("PostalCode cannot be empty.");
            error = false;
        } else if (!ValidationUtilities.isValidPostalCode(postalCode)) {
            postalCodeEditText.setError("Invalid PostalCode.");
            error = false;
        }

        if (TextUtils.isEmpty(province)) {
            provinceEditText.setError("Province cannot be empty.");
            error = false;
        } else if (province.length() < 2) {
            provinceEditText.setError("Invalid Province. [Province should be at least 2 characters]");
            error = false;
        }
        return error;

    }
}
