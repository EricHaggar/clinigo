package com.uottawa.camelclinic.model;

public class Employee extends User {

    public Employee(String email, String firstName, String lastName){
        super(email, firstName, lastName);
        setRole("Employee");
    }
}
