package com.uottawa.camelclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uottawa.camelclinic.R;
import com.uottawa.camelclinic.model.Employee;
import com.uottawa.camelclinic.model.Patient;
import com.uottawa.camelclinic.model.User;


public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference usersReference;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Spinner roleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUpVariables();
    }

    public void setUpVariables() {
        //Putting the content of the spinner
        String[] arraySpinner = new String[]{
                "Employee", "Patient"
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
        final String firstName = firstNameEditText.getText().toString();
        final String lastName = lastNameEditText.getText().toString();
        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final String role = roleSpinner.getSelectedItem().toString();


        EditText[] fields = {firstNameEditText, lastNameEditText, usernameEditText, passwordEditText};
        String[] inputs = {firstName, lastName, username, password};

        if (allInputsFilled(inputs, fields)) {
            String email = username + "@camelclinic.ca"; // To use Firebase Auth feature

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

    public boolean allInputsFilled(String[] inputs, EditText[] fields) {

        boolean inputsFilled = true;

        for (int i = 0; i < inputs.length; i++) {
            if (TextUtils.isEmpty(inputs[i])) {
                fields[i].setError("Field cannot be empty!");
                inputsFilled = false;
            }
        }
        return inputsFilled;
    }

    public User createUser(String role, String username, String firstName, String lastName) {
        User newUser = role.equals("Employee") ? new Employee(username, firstName, lastName) :
                new Patient(username, firstName, lastName);
        return newUser;
    }
}
