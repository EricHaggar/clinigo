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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.List;


public class AdminServicesActivity extends AppCompatActivity {

    EditText serviceEditText;
    EditText roleEditText;
    Button buttonAddService;
    ListView listViewServices;

    List<Service> services;
    DatabaseReference databaseServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_services);

        databaseServices = FirebaseDatabase.getInstance().getReference("services");


        serviceEditText = (EditText) findViewById(R.id.edit_service);
        roleEditText = (EditText) findViewById(R.id.edit_role);
        listViewServices = (ListView) findViewById(R.id.listViewServices);
        buttonAddService = (Button) findViewById(R.id.button_add);

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
                showUpdateDeleteDialog(service.getId(), service.getName(), service.getRole());
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

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Service service = postSnapshot.getValue(Service.class);
                    services.add(service);
                }

                ServiceAdapter servicesAdaptor = new ServiceAdapter(AdminServicesActivity.this, services);
                listViewServices.setAdapter(servicesAdaptor);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog(final String serviceId, String serviceName, String role) {

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

        String name = serviceEditText.getText().toString().trim();
        String role = roleEditText.getText().toString().trim();

        if (validServiceForm()) {

            String id = databaseServices.push().getKey();
            Service product = new Service(id, name, role);

            databaseServices.child(id).setValue(product);

            serviceEditText.setText("");
            roleEditText.setText("");

            Toast.makeText(this, "Service added", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validServiceForm() {

        boolean valid = true;

        String name = serviceEditText.getText().toString().trim();
        String role = roleEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            serviceEditText.setError("Service field cannot be empty.");
            valid = false;
        } else if (!ValidationUtilities.isValidName(name)) {
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
