package com.uottawa.camelclinic.model;

public class Employee extends User {

    public Employee(String username, String password, String firstName, String lastName){

        super(username, password, firstName, lastName);
        setRole("employee");
    }
}
