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

public class EmployeeAvailableServicesActivity extends AppCompatActivity {

    ListView servicesListView;
    ArrayList<Service> services;
    DatabaseReference servicesReference;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_services);

        servicesReference = FirebaseDatabase.getInstance().getReference("services");
        servicesListView = findViewById(R.id.list_services);
        services = new ArrayList<>();

        servicesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                if (service.getEmployees() == null) {
                    service.initializeEmptyEmployeeArray();
                }
                showAddServiceDialog(service);//Adding employee Id to the service
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

                    if ((service.getEmployees() == null) || (!clinicHasThisService(service.getEmployees())))
                        services.add(service);
                }

                ServiceAdapter serviceAdapter = new ServiceAdapter(EmployeeAvailableServicesActivity.this, services);
                servicesListView.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void showAddServiceDialog(final Service service) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_service_to_clinic_dialog, null);
        dialogBuilder.setView(dialogView);


        final Button buttonAdd = dialogView.findViewById(R.id.button_update);
        final Button buttonCancel = dialogView.findViewById(R.id.button_cancel);


        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ADD Service HERE!!!!!
                addService(service, userId);
                Intent intent = new Intent(getApplicationContext(), EmployeeServicesActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

    }

    public void addService(Service service, String userId) {

        DatabaseReference dR = servicesReference.child(service.getId());

        service.addEmployee(userId);
        dR.setValue(service);
        Toast.makeText(getApplicationContext(), "Service Added", Toast.LENGTH_SHORT).show();

    }

    public boolean clinicHasThisService(ArrayList<String> employees) {
        boolean returnValue = false;

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).equals(userId))
                returnValue = true;
        }

        return returnValue;
    }
}
