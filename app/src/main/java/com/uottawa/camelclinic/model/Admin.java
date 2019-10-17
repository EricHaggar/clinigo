package com.uottawa.camelclinic.model;

public class Admin extends User {

    public Admin(String username, String password, String firstName, String lastName){

        super(username, password, firstName, lastName);
        setRole("admin");
    }
}
