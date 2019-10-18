package com.uottawa.camelclinic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.uottawa.camelclinic.utilities.EditTextUtilities;
import com.uottawa.camelclinic.utilities.EmailUtilities;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersReference;
    private EditText usernameEditText;
    private EditText passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initVariables();
    }

    public void initVariables() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        usersReference = mDatabase.getReference("Users");
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
    }

    public void loginOnClick(View view) {

        final String username = usernameEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        EditText[] fields = {usernameEditText, passwordEditText};
        String[] inputs = {username, password};

        if (EditTextUtilities.allInputsFilled(inputs, fields)) {
            String email = EmailUtilities.createEmail(username); // To use Firebase Auth feature

            if (userIsAdmin(username, password)) {
                Intent intent = new Intent(getApplicationContext(), SuccessfulLoginActivity.class);
                intent.putExtra("welcomeMessage", "Welcome " + username + "! You are logged in as an Admin.");
                startActivity(intent);
                finish();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    final String userId = task.getResult().getUser().getUid();
                                    usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot data: dataSnapshot.getChildren()) {
                                                if (data.getKey().equals(userId)) {
                                                    final String role = data.child("role").getValue().toString();
                                                    Intent intent = new Intent(getApplicationContext(), SuccessfulLoginActivity.class);
                                                    intent.putExtra("welcomeMessage", "Welcome " + username + "! You are logged in as " + role + ".");
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Could not Authenticate!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    public boolean userIsAdmin(final String username, String password) {
        if (username.equals("admin") && password.equals("5T5ptQ")) {
            return true;
        }
        return false;
    }
}
