package com.uottawa.clinigo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class EditClinicInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String userId;
    private String selectedCountry;
    private EditText clinicNameEditText;
    private EditText phoneNumberEditText;
    private EditText descriptionEditText;
    private EditText addressEditText;
    private EditText cityEditText;
    private EditText provinceEditText;
    private CheckBox licensedCheckbox;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_clinic_info);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        initVariables();

        Spinner spinner = findViewById(R.id.update_clinic_country);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);

        int default_position = spinner_adapter.getPosition(selectedCountry);
        spinner.setSelection(default_position);
    }

    public void initVariables(){

        mDatabase = FirebaseDatabase.getInstance();
        usersReference = mDatabase.getReference().child("users").child(userId);
        clinicNameEditText = findViewById(R.id.update_clinic_name_);
        phoneNumberEditText = findViewById(R.id.update_clinic_phoneNumber);
        descriptionEditText = findViewById(R.id.update_clinic_description);
        addressEditText = findViewById(R.id.update_clinic_address);
        cityEditText = findViewById(R.id.update_clinic_city);
        provinceEditText = findViewById(R.id.update_clinic_province);

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
                        provinceEditText.setText(data.child("address").child("province").getValue().toString());
                        selectedCountry = data.child("address").child("country").getValue().toString();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    // update database information if all data is valid
    public void updateProfileInformation(View view){


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String country = parent.getItemAtPosition(position).toString();
        this.selectedCountry = country;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveChanges(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to update your Clinic Information ?");
        alertDialogBuilder.setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Test for yes", Toast.LENGTH_SHORT).show();
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
}
