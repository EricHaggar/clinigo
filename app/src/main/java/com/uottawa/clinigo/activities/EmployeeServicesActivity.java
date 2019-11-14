package com.uottawa.clinigo.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

        servicesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);

                showDeleteServiceDialog(service); //Deleting service from the Clinic
                return true;
            }
        });

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


    public void addServiceOnClick(View view) {
        Intent intent = new Intent(this, EmployeeAvailableServicesActivity.class);

        //Pass the user to the next Activity
        intent.putExtra("userId", userId);

        startActivity(intent);
    }

    public boolean clinicHasThisService(ArrayList<String> employees) {
        boolean returnValue = false;

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).equals(userId))
                returnValue = true;
        }

        return returnValue;
    }

    public void deleteService(Service service, String userId) {
        DatabaseReference dR = servicesReference.child(service.getId());
        service.removeEmployee(userId);

        dR.setValue(service);
        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_SHORT).show();
    }

    public void showDeleteServiceDialog(final Service service) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_service_from_clinic, null);
        dialogBuilder.setView(dialogView);


        final Button buttonDelete = dialogView.findViewById(R.id.button_add);
        final Button buttonCancel = dialogView.findViewById(R.id.button_cancel);


        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(service, userId);
                b.dismiss();

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

    }
}
