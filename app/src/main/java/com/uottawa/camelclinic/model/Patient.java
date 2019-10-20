package com.uottawa.camelclinic.model;

public class Patient extends User {

    public Patient(String email, String firstName, String lastName){
        super(email, firstName, lastName);
        setRole("Patient");
    }
}
