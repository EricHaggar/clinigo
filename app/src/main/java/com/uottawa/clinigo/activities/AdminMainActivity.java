package com.uottawa.clinigo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.uottawa.clinigo.R;
import com.uottawa.clinigo.model.Service;
import com.uottawa.clinigo.model.User;

import java.util.ArrayList;
import java.util.List;


public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
    }

    public void manageAccountsOnClick(View view) {
        Intent intent = new Intent(this, AdminUsersActivity.class);
        startActivity(intent);
    }

    public void manageServicesOnClick(View view) {
        Intent intent = new Intent(this, AdminServicesActivity.class);
        startActivity(intent);
    }

    public static class Admin extends User {

        private List<Service> services;

        public Admin(String id, String email, String firstName, String lastName) {
            super(id, email, firstName, lastName);
            setRole("Admin");
            services = new ArrayList<>();
        }

        public void addService(Service service) {
            services.add(service);
        }

        public void deleteService(Service service) {
            services.remove(service);
        }
    }
}

