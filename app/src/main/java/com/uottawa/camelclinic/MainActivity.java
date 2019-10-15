package com.uottawa.camelclinic;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

import android.os.Bundle;

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
}
