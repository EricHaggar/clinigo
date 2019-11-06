package com.uottawa.camelclinic.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;


public class AdminUsersActivity extends AppCompatActivity {

    ListView usersListView;
    ArrayList<User> users;
    DatabaseReference usersReference;
    FloatingActionButton addUserFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);
    }

    usersReference = FirebaseDatabase.getInstance().getReference("users");
    usersListView = findViewById(R.id.list_users);
    users = new ArrayList<>();

    usersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            User users = users.get(i);
            showUpdateServiceDialog(user.getId(), user.getName(), user.getRole());
            return true;
        }
    });


    TextView emptyText = findViewById(R.id.text_empty_users);
    usersListView.setEmptyView(emptyText);

    @Override
    protected void onStart() {
        super.onStart();

        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }

                UserAdaptor userAdapter = new UserAdaptor(AdminUsersActivity.this, users);
                usersListView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void showAddUserDialog() {

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

    public void showUpdateUserDialog(final String userId, String userName, String role) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_user_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText userEditText = (EditText) dialogView.findViewById(R.id.edit_user); // not sure if i need to keep these ones yet
        final EditText roleEditText = (EditText) dialogView.findViewById(R.id.edit_role);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.button_update_user); ///
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.button_delete_user);

        dialogBuilder.setTitle("Delete User");
        serviceEditText.setText(userName);
        roleEditText.setText(role);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userEditText.getText().toString().trim();
                String role = roleEditText.getText().toString().trim(); ///

                if (validUserForm(userEditText, roleEditText)) { ///
                    updateUser(userId, name, role);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(userId);
                b.dismiss();
            }
        });

        public void deleteUser(String id) {

            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("user").child(id);

            dR.removeValue();
            Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_SHORT).show();
        }
    }

}
