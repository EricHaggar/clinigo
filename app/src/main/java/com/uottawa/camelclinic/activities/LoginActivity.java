package com.uottawa.camelclinic.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.uottawa.camelclinic.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void loginOnClick(View view) {
        Intent intent = new Intent(this, SuccessfulLoginActivity.class);

        startActivity(intent);
    }
}
