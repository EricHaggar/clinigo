package com.uottawa.clinigo.activities;

import android.app.AlertDialog;
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
import com.uottawa.clinigo.adapters.UserAdapter;
import com.uottawa.clinigo.model.User;

import java.util.ArrayList;


public class AdminUsersActivity extends AppCompatActivity {

    ListView usersListView;
    ArrayList<User> users;
    DatabaseReference usersReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        usersReference = FirebaseDatabase.getInstance().getReference("users");
        usersListView = findViewById(R.id.list_users);
        users = new ArrayList<>();

        usersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i);
                showDeleteUserDialog(user.getId());
                return true;
            }
        });

        TextView emptyText = findViewById(R.id.text_empty_users);
        usersListView.setEmptyView(emptyText);

    }

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

                UserAdapter userAdapter = new UserAdapter(AdminUsersActivity.this, users);
                usersListView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void showDeleteUserDialog(final String userId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_user_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button deleteButton = dialogView.findViewById(R.id.button_add);
        final Button cancelButton = dialogView.findViewById(R.id.button_cancel);

        dialogBuilder.setTitle("Delete User");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(userId);
                alertDialog.dismiss();
            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }

        });

    }

    public void deleteUser(String id) {
        DatabaseReference dR = usersReference.child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_SHORT).show();
    }
}


