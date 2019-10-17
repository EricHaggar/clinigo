package com.uottawa.camelclinic.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

import android.os.Bundle;
import com.uottawa.camelclinic.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToUserTypeSelectionScreen (View view) {
        Intent intent = new Intent(this, UserTypeSelection.class);

        startActivity(intent);
    }

    public void goToSignInPage (View view) {
        Intent intent = new Intent(this, NewLogIn.class);

        startActivity(intent);
    }
}
