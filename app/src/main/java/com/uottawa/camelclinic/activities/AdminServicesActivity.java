package com.uottawa.camelclinic.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.camelclinic.R;
import com.uottawa.camelclinic.adapters.ServiceAdapter;
import com.uottawa.camelclinic.model.Service;
import com.uottawa.camelclinic.utilities.ValidationUtilities;

import java.util.ArrayList;


public class AdminServicesActivity extends AppCompatActivity {

    ListView servicesListView;
    ArrayList<Service> services;
    DatabaseReference servicesReference;
    FloatingActionButton addServiceFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

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

        addServiceFab = findViewById(R.id.fab_add_service);
        addServiceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddServiceDialog();
            }
        });
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

                ServiceAdapter serviceAdapter = new ServiceAdapter(AdminServicesActivity.this, services);
                servicesListView.setAdapter(serviceAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void showAddServiceDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_service_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText serviceEditText = (EditText) dialogView.findViewById(R.id.edit_service);
        final EditText roleEditText = (EditText) dialogView.findViewById(R.id.edit_role);
        final Button addButton = (Button) dialogView.findViewById(R.id.button_add_service);

        dialogBuilder.setTitle("Add Service");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = serviceEditText.getText().toString().trim();
                String role = roleEditText.getText().toString().trim();

                if (validServiceForm(serviceEditText, roleEditText)) {
                    addService(name, role);
                    alertDialog.dismiss();
                }
            }
        });


    }

    public void showUpdateServiceDialog(final String serviceId, String serviceName, String role) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_service_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText serviceEditText = (EditText) dialogView.findViewById(R.id.edit_service);
        final EditText roleEditText = (EditText) dialogView.findViewById(R.id.edit_role);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.button_update_service);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.button_delete_service);

        dialogBuilder.setTitle("Edit Service");
        serviceEditText.setText(serviceName);
        roleEditText.setText(role);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = serviceEditText.getText().toString().trim();
                String role = roleEditText.getText().toString().trim();

                if (validServiceForm(serviceEditText, roleEditText)) {
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

    public void deleteService(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(id);

        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Service Deleted", Toast.LENGTH_LONG).show();
    }

    public void updateService(String id, String name, String role) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("services").child(id);

        Service service = new Service(id, name, role);
        dR.setValue(service);

        Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_LONG).show();

    }

    public void addService(String serviceName, String role) {

        String id = servicesReference.push().getKey();
        Service product = new Service(id, serviceName, role);

        servicesReference.child(id).setValue(product);

        Toast.makeText(this, "Service added", Toast.LENGTH_LONG).show();
    }

    public boolean validServiceForm(EditText serviceEditText, EditText roleEditText) {

        boolean valid = true;

        String serviceName = serviceEditText.getText().toString().trim();
        String role = roleEditText.getText().toString().trim();

        if (TextUtils.isEmpty(serviceName)) {
            serviceEditText.setError("Service field cannot be empty.");
            valid = false;
        } else if (!ValidationUtilities.isValidName(serviceName)) {
            serviceEditText.setError("The given service name is invalid.");
            valid = false;
        }

        if (TextUtils.isEmpty(role)) {
            roleEditText.setError("Role field cannot be empty.");
            valid = false;
        } else if (!ValidationUtilities.isValidName(role)) {
            roleEditText.setError("The given role name is invalid.");
            valid = false;
        }

        return valid;
    }
}
