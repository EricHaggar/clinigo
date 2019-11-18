package com.uottawa.clinigo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.model.Address;
import com.uottawa.clinigo.model.ClinicInfo;
import com.uottawa.clinigo.utilities.ValidationUtilities;

public class EditClinicInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String userId;
    private String selectedCountry;
    private EditText clinicNameEditText;
    private EditText phoneNumberEditText;
    private EditText descriptionEditText;
    private EditText addressEditText;
    private EditText cityEditText;
    private EditText postalCodeEditText;
    private EditText provinceEditText;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersReference;
    private ClinicInfo clinicInfo;
    private CheckBox licensedCheckbox;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinner_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clinic_info);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        spinner = findViewById(R.id.update_clinic_country);
        spinner_adapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);

        initVariables();
    }

    public void initVariables(){

        mDatabase = FirebaseDatabase.getInstance();
        usersReference = mDatabase.getReference().child("users").child(userId);
        clinicNameEditText = findViewById(R.id.update_clinic_name_);
        phoneNumberEditText = findViewById(R.id.update_clinic_phoneNumber);
        descriptionEditText = findViewById(R.id.update_clinic_description);
        addressEditText = findViewById(R.id.update_clinic_address);
        cityEditText = findViewById(R.id.update_clinic_city);
        postalCodeEditText = findViewById(R.id.update_clinic_postal_code);
        provinceEditText = findViewById(R.id.update_clinic_province);
        licensedCheckbox = findViewById(R.id.update_clinic_license);

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    if(data.getKey().equals("clinicInfo")) {
                        clinicNameEditText.setText(data.child("name").getValue().toString());
                        phoneNumberEditText.setText(data.child("phoneNumber").getValue().toString());
                        descriptionEditText.setText(data.child("description").getValue().toString());
                        addressEditText.setText(data.child("address").child("streetName").getValue().toString());
                        cityEditText.setText(data.child("address").child("city").getValue().toString());
                        postalCodeEditText.setText(data.child("address").child("postalCode").getValue().toString());
                        provinceEditText.setText(data.child("address").child("province").getValue().toString());
                        selectedCountry = data.child("address").child("country").getValue().toString();
                        int default_position = spinner_adapter.getPosition(selectedCountry);
                        spinner.setSelection(default_position);
                        String isPreviouslyLicensed = data.child("license").getValue().toString();
                        if(isPreviouslyLicensed.equals("true")){
                            licensedCheckbox.setChecked(true);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String country = parent.getItemAtPosition(position).toString();
        this.selectedCountry = country;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
    // update database information if all data is valid
    public boolean updateProfileInformation(View view){

        final String clinicName = clinicNameEditText.getText().toString().trim();
        final String phoneNumber = phoneNumberEditText.getText().toString().trim();
        final String description = descriptionEditText.getText().toString().trim();
        final String address = addressEditText.getText().toString().trim();
        final String city = cityEditText.getText().toString().trim();
        final String postalCode = postalCodeEditText.getText().toString().trim();
        final String province = provinceEditText.getText().toString();
        final String country = selectedCountry;
        final boolean licensed = licensedCheckbox.isChecked();


        final DatabaseReference userRef = usersReference.child("clinicInfo");
        if (validProfileForm(clinicName, phoneNumber, description, address, city, postalCode, province)) {

            Address newAddress = new Address(address, city, postalCode, province, country);
            clinicInfo = new ClinicInfo(clinicName, phoneNumber, newAddress, description, licensed);
            try {
                userRef.setValue(clinicInfo);
                Intent intent = new Intent(getApplicationContext(), EmployeeMainActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
                return true;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error," + e, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return false;
    }


    public void saveChanges(final View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to update your Clinic Information ?");
        alertDialogBuilder.setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(updateProfileInformation(view)) {
                    Toast.makeText(getApplicationContext(), "Your Changes have been saved !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public boolean validProfileForm(String clinicName, String phoneNumber, String description, String streetName, String city, String postalCode, String province) {

        boolean error = true;

        if (TextUtils.isEmpty(clinicName)) {
            clinicNameEditText.setError("Clinic Name cannot be empty.");
            error = false;
        } else if (clinicName.length() < 2) {
            clinicNameEditText.setError("Invalid Clinic Name. [Clinic name should be at least 2 characters]");
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
        } else if (TextUtils.isEmpty(streetName)) {
            addressEditText.setError("Street Name cannot be empty.");
            error = false;
        }
        if (streetName.length() < 4) {
            addressEditText.setError("Invalid Street Name. [Street name should be at least 4 characters]");
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
