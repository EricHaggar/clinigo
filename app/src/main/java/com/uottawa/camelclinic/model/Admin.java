package com.uottawa.camelclinic.model;

public class Admin extends User {

    public Admin(String username, String firstName, String lastName){
        super(username, firstName, lastName);
        setRole("Admin");
    }
}
