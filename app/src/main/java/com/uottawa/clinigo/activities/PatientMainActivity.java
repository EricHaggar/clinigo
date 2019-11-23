package com.uottawa.clinigo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.model.Service;
import com.uottawa.clinigo.model.User;
import java.util.Arrays;

public class PatientMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RadioGroup sortRadioGroup;
    private RadioButton sortRadioButton;

    //These contain the available adresses, working hours and services for the patient to choose when searching for a clinic
    private ArrayList<String>  availableAddresses;
    private ArrayList<String> availableCities;
    private ArrayList<String> availableWorkingHours;
    private ArrayList<String> availableServices;
    private ArrayList<User> availableClinics;

    DatabaseReference usersReference;
    DatabaseReference servicesReference;

    Spinner spinner;
    ArrayAdapter<String> spinner_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main);

        // Sort Radio Buttons
        sortRadioGroup = findViewById(R.id.rb_group_sort_types);
        sortRadioGroup.check(R.id.rbutton_address); // Default sort type is by address

        //Database references
        usersReference = FirebaseDatabase.getInstance().getReference("users");
        servicesReference = FirebaseDatabase.getInstance().getReference("services");

        //Populate the available Arrays when the instance is created
        availableClinics = new ArrayList<>();
        availableAddresses = new ArrayList<>();
        availableWorkingHours = new ArrayList<String>(
                Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        availableServices = new ArrayList<>();
        availableCities = new ArrayList<>();

        this.initVariables();

        // should be moved to adapters later.
        spinner = findViewById(R.id.spinner_options);
        spinner.setOnItemSelectedListener(this);

    }

    private void initVariables() {


        //Getting the Address for All the clinics
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                availableClinics.clear();
                availableAddresses.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);
                    if (user.getRole().equals("Employee")) {
                        availableClinics.add(user);

                        String address = postSnapshot.child("clinicInfo").child("location").child("address").getValue().toString();
                        String city = postSnapshot.child("clinicInfo").child("location").child("city").getValue().toString();
                        availableAddresses.add(address);

                        if (!availableCities.contains(city))
                            availableCities.add(city);
                    }
                }

                spinner_adapter =
                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_item, availableAddresses);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinner_adapter);
                spinner.setSelection(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Getting all availables services offered by the different clinics
        servicesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableServices.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Service service = postSnapshot.getValue(Service.class);

                    if (service.getEmployees() != null) {
                        String serviceName = postSnapshot.child("name").getValue().toString();
                        availableServices.add(serviceName);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String day = parent.getItemAtPosition(position).toString();
//        selectedDay = day;
//        updateTextViewsFromWidget();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void checkSortBySelection(View view) {

        int radioButtonId = sortRadioGroup.getCheckedRadioButtonId();
        sortRadioButton = findViewById(radioButtonId);

        if (sortRadioButton.getText().toString().equals("Address")) {
            spinner_adapter =
                    new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_item, availableAddresses);
            spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinner_adapter);

        } else if (sortRadioButton.getText().toString().equals("Working Hours")) {
            spinner_adapter =
                    new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_item, availableWorkingHours);
            spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinner_adapter);

        } else if (sortRadioButton.getText().toString().equals("Type of Services")){
            spinner_adapter =
                    new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_item, availableServices);
            spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinner_adapter);

        } else if (sortRadioButton.getText().toString().equals("City")){
            spinner_adapter =
                    new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_item, availableCities);
            spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinner_adapter);

        }

    }





//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_search, menu);
//        MenuItem menuItem = menu.findItem(R.id.menu_search);
//
//        SearchView searchView = (SearchView) menuItem.getActionView();
//
//        return super.onCreateOptionsMenu(menu);
//    }
}
