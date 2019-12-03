package com.uottawa.clinigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uottawa.clinigo.R;
import com.uottawa.clinigo.utilities.ValidationUtilities;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersReference;
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initVariables();
    }

    public void initVariables() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        usersReference = mDatabase.getReference("users");
        emailEditText = findViewById(R.id.edit_email);
        passwordEditText = findViewById(R.id.edit_password);

        emailEditText.requestFocus(); // Place cursor on email when activity is created
    }

    public void loginOnClick(View view) {

        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        if (validLoginForm()) {
            if (ValidationUtilities.isValidAdmin(email, password)) {
                Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                startActivity(intent);
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final String userId = task.getResult().getUser().getUid();
                                    usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            boolean userWasDeleted = true; //To solve not being able to delete user from admin account
                                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                if (data.getKey().equals(userId)) {
                                                    userWasDeleted = false;
                                                    final String role = data.child("role").getValue().toString();
                                                    final String firstName = data.child("firstName").getValue().toString();
                                                    Intent intent;
                                                    if (!role.equals("Employee")) {
                                                        intent = new Intent(getApplicationContext(), PatientMainActivity.class);
                                                        intent.putExtra("userId", userId);
                                                    } else if (role.equals("Employee") & data.hasChild("clinicInfo")) {
                                                        intent = new Intent(getApplicationContext(), EmployeeMainActivity.class);
                                                        intent.putExtra("userId", userId);
                                                    } else {
                                                        intent = new Intent(getApplicationContext(), ClinicInfoActivity.class);
                                                        intent.putExtra("welcomeMessage", "Welcome " + firstName + "! You are logged in as " + role + "." + " Manage your services and working hours below.");

                                                        //Pass the user to the next Activity
                                                        intent.putExtra("userId", userId);
                                                    }

                                                    startActivity(intent);
                                                }
                                            }
                                            if (userWasDeleted) {
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                user.delete()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(getApplicationContext(), "This user does not exist anymore.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(getApplicationContext(), "Firebase Database Error.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        }
    }

    public boolean validLoginForm() {

        boolean valid = true;

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email field cannot be empty.");
            valid = false;
        } else if (!ValidationUtilities.isValidEmail(email)) {
            emailEditText.setError("The given email is invalid.");
            valid = false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password field cannot be empty.");
            valid = false;
        } else if (password.length() < 6) {
            passwordEditText.setError("The given password is invalid. [Password should be at least 6 characters]");
            valid = false;
        }

        return valid;
    }
}
