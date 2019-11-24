package com.uottawa.clinigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.adapters.ClinicAdapter;
import com.uottawa.clinigo.adapters.UserAdapter;
import com.uottawa.clinigo.model.ClinicInfo;
import com.uottawa.clinigo.model.Service;
import com.uottawa.clinigo.model.User;
import java.util.Arrays;
import com.uottawa.clinigo.model.Employee;
import com.uottawa.clinigo.model.WorkingHours;

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

    ListView clinicListView;

    Spinner spinner;
    ArrayAdapter<String> spinner_adapter;
    private String patientClinicChoice;

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
        clinicListView = findViewById(R.id.list_clinics);

        TextView pressOnSearchText = findViewById(R.id.text_press_on_search);
        clinicListView.setEmptyView(pressOnSearchText);

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

    public void getClinicWithAddress(final String address) {

        final ArrayList<Employee> clincsWithThatAddress = new ArrayList<>();

        //Getting the User (Clinic) with the appropriate address
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);


                    if (user.getRole().equals("Employee")) {
                        Employee emp = postSnapshot.getValue(Employee.class);

                        String currentAddress = postSnapshot.child("clinicInfo").child("location").child("address").getValue().toString();

                        if (currentAddress.equals(address))
                            clincsWithThatAddress.add(emp);

                    }
                }
                displayResults(clincsWithThatAddress);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getClinicInCity(final String city) {

        final ArrayList<Employee> clinicsInCity = new ArrayList<>();

        //Getting the User (Clinic) in the Appropriate city
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);


                    if (user.getRole().equals("Employee")) {
                        Employee emp = postSnapshot.getValue(Employee.class);

                        String currentAddress = postSnapshot.child("clinicInfo").child("location").child("city").getValue().toString();

                        if (currentAddress.equals(city))
                            clinicsInCity.add(emp);

                    }
                }
                displayResults(clinicsInCity);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getClinicForService(final String serviceName) {
        final ArrayList<Employee> clinicsForService = new ArrayList<>();


        final ArrayList<String> clinicsIds = new ArrayList<>();


        //Getting the User (Clinic) for a specific service
        servicesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Service service = postSnapshot.getValue(Service.class);


                    if (service.getName().equals(serviceName)) {
                        for (int i=0; i< service.getEmployees().size(); i++) {
                            clinicsIds.add(service.getEmployees().get(i));
                        }
                    }

                }

                usersReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            if (clinicsIds.contains(postSnapshot.getKey())) {
                                Employee emp = postSnapshot.getValue(Employee.class);
                                clinicsForService.add(emp);
                            }

                        }
                        displayResults(clinicsForService);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getIndexForSelectedDay(String day) {

        if (day.equals("Monday"))
            return 0;
        else if (day.equals("Tuesday"))
            return 1;
        else if (day.equals("Wednesday"))
            return 2;
        else if (day.equals("Thursday"))
            return 3;
        else if (day.equals("Friday"))
            return 4;
        else if (day.equals("Saturday"))
            return 5;
        else if (day.equals("Sunday"))
            return 6;
        else
            return 0;
    }

    public void getClinicForWorkingHours(String day) {
        final int dayIndex = this.getIndexForSelectedDay(day);

        final ArrayList<Employee> clinicsForWorkingHours = new ArrayList<>();

        //Getting the User (Clinic) for the appropriate working hours/day
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);

                    if (user.getRole().equals("Employee")) {
                        Employee emp = postSnapshot.getValue(Employee.class);

                        WorkingHours wh = postSnapshot.child("workingHours").getValue(WorkingHours.class);

                        if (!wh.getStartTimes().get(dayIndex).equals("--"))
                            clinicsForWorkingHours.add(emp);

                    }
                }
                displayResults(clinicsForWorkingHours);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getSearchResults(View view) {

        String optionsVariable = spinner.getSelectedItem().toString();

        int radioButtonId = sortRadioGroup.getCheckedRadioButtonId();
        sortRadioButton = findViewById(radioButtonId);

        if (sortRadioButton.getText().toString().equals("Address")) {
            getClinicWithAddress(optionsVariable);
        } else if (sortRadioButton.getText().toString().equals("Working Hours")) {
            getClinicForWorkingHours(optionsVariable);
        } else if (sortRadioButton.getText().toString().equals("Type of Services")){
            getClinicForService(optionsVariable);
        } else if (sortRadioButton.getText().toString().equals("City")){
            getClinicInCity(optionsVariable);
        }

    }

    public void displayResults(ArrayList<Employee> results) {

        final ClinicAdapter clinicAdapter = new ClinicAdapter(PatientMainActivity.this, results);
        clinicListView.setAdapter(clinicAdapter);
        clinicListView.setClickable(true);
        clinicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                patientClinicChoice = clinicAdapter.getItem(position).getId();
                patientBookingActivity();
            }
        });
    }
    public void patientBookingActivity(){

        Intent intent = new Intent(this, BookingActivity.class);
        intent.putExtra("userId", patientClinicChoice);
        startActivity(intent);
    }

}
