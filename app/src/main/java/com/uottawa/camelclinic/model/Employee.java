package com.uottawa.camelclinic.model;

public class Employee extends User {

    public Employee(String username, String firstName, String lastName){
        super(username, firstName, lastName);
        setRole("Employee");
    }
}
