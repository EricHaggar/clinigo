package com.uottawa.camelclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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
    private RadioGroup rolesRadioGroup;
    private RadioButton roleRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initVariables();
    }

    public void initVariables() {
        // Roles Radio Buttons
        rolesRadioGroup = findViewById(R.id.rb_group_user_types);
        rolesRadioGroup.check(R.id.rbutton_employee); // Default user type is employee

        // Firebase Authentication and Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        usersReference = mDatabase.getReference("Users");

        // Input Fields
        firstNameEditText = findViewById(R.id.edit_first_name);
        lastNameEditText = findViewById(R.id.lastName);
        usernameEditText = findViewById(R.id.edit_username);
        passwordEditText = findViewById(R.id.edit_password);
    }

    public void welcomeOnClick(View view) {

        // Fetch all field values
        final String firstName = firstNameEditText.getText().toString().trim();
        final String lastName = lastNameEditText.getText().toString().trim();
        final String username = usernameEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        int radioButtonId = rolesRadioGroup.getCheckedRadioButtonId();
        roleRadioButton = findViewById(radioButtonId);
        final String role = roleRadioButton.getText().toString();

        EditText[] fields = {firstNameEditText, lastNameEditText, usernameEditText, passwordEditText};
        String[] inputs = {firstName, lastName, username, password};

        if (EditTextUtilities.allInputsFilled(inputs, fields)) {
            String email = EmailUtilities.createEmail(username); // To use Firebase Auth feature

            if (role.equals("Admin")) {

                if (username.equals("admin") && password.equals("5T5ptQ")) {
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
                    Toast.makeText(getApplicationContext(), "Admin Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            } else {

                if (username.equals("admin")) {
                    usernameEditText.setError("Invalid Username For Non-Admin!");
                    return;
                }

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

                                    String message;
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        message = e.getMessage();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        message = e.getMessage();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        message = e.getMessage();
                                    } catch (Exception e) {
                                        message = e.getMessage();
                                    }

                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
