package com.uottawa.camelclinic.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.uottawa.camelclinic.R;

public class UserTypeSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type_selection);
    }

    public void goToCreateNewAccountActivity(View view) {
        Intent intent = new Intent(this, CreatingNewAccount.class);

        startActivity(intent);
    }
}
