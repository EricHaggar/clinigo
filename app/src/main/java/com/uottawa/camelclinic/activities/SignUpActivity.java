package com.uottawa.camelclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uottawa.camelclinic.R;
import com.uottawa.camelclinic.model.Employee;
import com.uottawa.camelclinic.model.Patient;
import com.uottawa.camelclinic.model.User;
import com.uottawa.camelclinic.utilities.ValidationUtilities;


public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersReference;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
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
        lastNameEditText = findViewById(R.id.edit_last_name);
        emailEditText = findViewById(R.id.edit_email);
        passwordEditText = findViewById(R.id.edit_password);

        firstNameEditText.requestFocus(); // Place cursor on firstName when activity is created
    }

    public void signUpOnClick(View view) {

        // Fetch all field values
        final String firstName = firstNameEditText.getText().toString().trim();
        final String lastName = lastNameEditText.getText().toString().trim();
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        int radioButtonId = rolesRadioGroup.getCheckedRadioButtonId();
        roleRadioButton = findViewById(radioButtonId);
        final String role = roleRadioButton.getText().toString();

        if (validSignUpForm()) {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                final String id = mAuth.getCurrentUser().getUid();
                                final User newUser = createUser(id, role, email, firstName, lastName);

                                usersReference
                                        .child(mAuth.getCurrentUser().getUid()).setValue(newUser)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent = new Intent(getApplicationContext(), SuccessfulLoginActivity.class);
                                                    intent.putExtra("welcomeMessage", "Welcome " + firstName + "! You are logged in as " + role + ".");
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Firebase Database Error!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    passwordEditText.setError(e.getMessage());
                                    passwordEditText.requestFocus();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    emailEditText.setError(e.getMessage());
                                    emailEditText.requestFocus();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }
    }

    public User createUser(String id, String role, String email, String firstName, String lastName) {
        return role.equals("Employee") ? new Employee(id, email, firstName, lastName) : new Patient(id, email, firstName, lastName);
    }

    public boolean validSignUpForm() {
        boolean valid = true;

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            firstNameEditText.setError("First Name field cannot be empty.");
            valid = false;
        } else if (!ValidationUtilities.isValidName(firstName)) {
            firstNameEditText.setError("The given first name is invalid.");
            valid = false;
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameEditText.setError("Last Name field cannot be empty.");
            valid = false;
        } else if (!ValidationUtilities.isValidName(lastName)) {
            lastNameEditText.setError("The given last name is invalid.");
            valid = false;
        }

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
