package com.uottawa.camelclinic.model;

public class Admin extends User {

    public Admin(String email, String firstName, String lastName){
        super(email, firstName, lastName);
        setRole("Admin");
    }
}
