package com.uottawa.clinigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.adapters.ServiceAdapter;
import com.uottawa.clinigo.model.Service;

import java.util.ArrayList;

public class EmployeeServicesActivity extends AppCompatActivity {

    ListView servicesListView;
    ArrayList<Service> services;
    DatabaseReference servicesReference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_services);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        servicesReference = FirebaseDatabase.getInstance().getReference("services");
        servicesListView = findViewById(R.id.list_services);
        services = new ArrayList<>();

        //TODO: Below is to be overwritten when implementing the option for the user to delete the service

//        servicesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Service service = services.get(i);
//                if (service.getEmployees() == null) {
//                    service.initializeEmptyEmployeeArray();
//                }
//                showUpdateServiceDialog(service);//Adding employee Id to the service
//                return true;
//            }
//        });

        TextView emptyText = findViewById(R.id.text_empty_services);
        servicesListView.setEmptyView(emptyText);

        userId = getIntent().getStringExtra("userId");
    }

    @Override
    protected void onStart() {
        super.onStart();

        servicesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                services.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    if (service.getEmployees() != null) {
                        if (clinicHasThisService(service.getEmployees())) // check if user Id is in the arraylist
                            services.add(service);
                    }
                }

                ServiceAdapter serviceAdapter = new ServiceAdapter(EmployeeServicesActivity.this, services);
                servicesListView.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void goToAddNewServicesPageOnClick(View view) {
        Intent intent = new Intent(this, AvailableServicesActivity.class);

        //Pass the user to the next Activity
        intent.putExtra("userId",userId);

        startActivity(intent);
    }

    public boolean clinicHasThisService (ArrayList<String> employees) {
        boolean returnValue = false;

        for (int i =0; i < employees.size(); i++) {
            if (employees.get(i).equals(userId))
                returnValue = true;
        }

        return returnValue;
    }
}
