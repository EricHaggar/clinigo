package com.uottawa.clinigo.model;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

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
