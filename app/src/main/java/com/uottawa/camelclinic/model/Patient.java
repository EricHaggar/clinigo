package com.uottawa.camelclinic.model;

public class Patient extends User {

    public Patient(String username, String password, String firstName, String lastName){

        super(username, password, firstName, lastName);
        setRole("patient");
    }
}
