package com.uottawa.camelclinic.model;

public class Admin extends User {

    public Admin(String id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
        setRole("Admin");
    }
}
