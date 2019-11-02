package com.uottawa.camelclinic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.uottawa.camelclinic.R;
import androidx.annotation.NonNull;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.camelclinic.model.Service;

public class ServicesActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextRole;
    Button buttonAddService;
    ListView listViewServices;

    List<Service> services;
    DatabaseReference databaseServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        databaseServices = FirebaseDatabase.getInstance().getReference("services");


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextRole = (EditText) findViewById(R.id.editTextRole);
        listViewServices = (ListView) findViewById(R.id.listViewServices);
        buttonAddService = (Button) findViewById(R.id.addButton);

        services = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addService();
            }
        });

        listViewServices.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Service service = services.get(i);
                showUpdateDeleteDialog(service.getId(), service.getName());
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                services.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Service service = postSnapshot.getValue(Service.class);
                    services.add(service);
                }

                ServiceList servicesAdaptor = new ServiceList(ServicesActivity.this, services);
                listViewServices.setAdapter(servicesAdaptor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String serviceId, String serviceName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextRole  = (EditText) dialogView.findViewById(R.id.editTextRole);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateService);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteService);

        dialogBuilder.setTitle(serviceName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String role = editTextRole.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateService(serviceId, name, role);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteService(serviceId);
                b.dismiss();
            }
        });
    }

    private void deleteService(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(id);

        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_LONG).show();
    }

    private void updateService(String id, String name, String role) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(id);

        Service service = new Service(id, name, role);
        dR.setValue(service);

        Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_LONG).show();

    }

    private void addService() {

        String name = editTextName.getText().toString().trim();
        String role = editTextRole.getText().toString().trim();

        if (!TextUtils.isEmpty(name)){

            String id = databaseServices.push().getKey();
            Service product = new Service(id, name, role);

            databaseServices.child(id).setValue(product);

            editTextName.setText("");
            editTextRole.setText("");

            Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
