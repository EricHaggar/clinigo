package com.uottawa.clinigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.adapters.ServiceAdapter;
import com.uottawa.clinigo.adapters.UserAdapter;
import com.uottawa.clinigo.model.Service;
import com.uottawa.clinigo.model.User;

import java.util.ArrayList;

public class AvailableServicesActivity extends AppCompatActivity {

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
                showUpdateServiceDialog(service.getId(), service.getName(), service.getRole());
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
                    services.add(service);
                }

                ServiceAdapter serviceAdapter = new ServiceAdapter(AvailableServicesActivity.this, services);
                servicesListView.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public void showUpdateServiceDialog(final String serviceId, final String serviceName, final String role) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_service_to_clinic_dialog, null);
        dialogBuilder.setView(dialogView);


        final Button buttonAdd = dialogView.findViewById(R.id.button_add_service);
        final Button buttonCancel = dialogView.findViewById(R.id.button_cancel);

        //dialogBuilder.setTitle("Add Service");

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ADD Service HERE!!!!!
                addService(serviceId, serviceName, role, userId);

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

    }

    public void addService(String serviceId, String name, String role, String userId) {

        DatabaseReference dR = servicesReference.child(serviceId);

        Service service = new Service(serviceId, name, role);
        service.addEmployee(userId);
        dR.setValue(service);
        Toast.makeText(getApplicationContext(), "Service Added", Toast.LENGTH_SHORT).show();

        System.out.println("ADEL-DEBUG");
        System.out.println(service.getEmployees());

        //Adding a new service as a test
//        String id = servicesReference.push().getKey();
//        Service product = new Service(id, "TESTING TESTING", role);
//        product.addEmployee(userId);//Crashes HERE
//        servicesReference.child(id).setValue(product);
//        Toast.makeText(this, "Service added", Toast.LENGTH_SHORT).show();
    }
}
