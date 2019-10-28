package com.uottawa.camelclinic.model;

public class Patient extends User {

    public Patient(String id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
        setRole("Patient");
    }
}
