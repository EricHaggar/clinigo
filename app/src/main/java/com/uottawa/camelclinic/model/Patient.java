package com.uottawa.camelclinic.model;

public class Patient extends User {

    public Patient(String username, String firstName, String lastName){
        super(username, firstName, lastName);
        setRole("patient");
    }
}
