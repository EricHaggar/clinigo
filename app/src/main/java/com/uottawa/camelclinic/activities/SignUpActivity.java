package com.uottawa.camelclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.camelclinic.R;
import com.uottawa.camelclinic.model.Admin;
import com.uottawa.camelclinic.model.Employee;
import com.uottawa.camelclinic.model.Patient;
import com.uottawa.camelclinic.model.User;
import com.uottawa.camelclinic.utilities.EditTextUtilities;
import com.uottawa.camelclinic.utilities.EmailUtilities;


public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersReference;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initVariables();
    }

    public void initVariables() {
        //Putting the content of the spinner
        String[] arraySpinner = new String[]{
                "Employee", "Patient", "Admin"
        };
        roleSpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        usersReference = mDatabase.getReference("Users");

        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
    }

    public void welcomeOnClick(View view) {

        // Fetch all field values
        final String firstName = firstNameEditText.getText().toString().trim();
        final String lastName = lastNameEditText.getText().toString().trim();
        final String username = usernameEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();
        final String role = roleSpinner.getSelectedItem().toString().trim();


        EditText[] fields = {firstNameEditText, lastNameEditText, usernameEditText, passwordEditText};
        String[] inputs = {firstName, lastName, username, password};

        if (EditTextUtilities.allInputsFilled(inputs, fields)) {
            String email = EmailUtilities.createEmail(username); // To use Firebase Auth feature

            if (role.equals("Admin") && username.equals("admin") && password.equals("5T5ptQ")) {
                usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int numOfAdmins = 0;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (data.child("role").getValue().toString().equals(role)) {
                                numOfAdmins++;
                            }
                        }
                        if (numOfAdmins >= 1) {
                            Toast.makeText(getApplicationContext(), "Admin Account Already Exists!", Toast.LENGTH_SHORT).show();
                        } else {
                            String id = usersReference.push().getKey();
                            usersReference.child(id).setValue(new Admin(username, firstName, lastName));
                            Intent intent = new Intent(getApplicationContext(), SuccessfulLoginActivity.class);
                            intent.putExtra("welcomeMessage", "Welcome " + username + "! You are logged in as " + role + ".");
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {

                final User newUser = createUser(role, username, firstName, lastName);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    usersReference
                                            .child(mAuth.getCurrentUser().getUid()).setValue(newUser)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Intent intent = new Intent(getApplicationContext(), SuccessfulLoginActivity.class);
                                                        intent.putExtra("welcomeMessage", "Welcome " + username + "! You are logged in as " + role + ".");
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Firebase Database Error!", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Firebase Authentication Error!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        }
    }

    public User createUser(String role, String username, String firstName, String lastName) {
        return role.equals("Employee") ? new Employee(username, firstName, lastName) : new Patient(username, firstName, lastName);
    }
}
