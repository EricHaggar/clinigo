package com.uottawa.clinigo.model;

public class Employee extends User {

    public Employee(String id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
        setRole("Employee");
    }
}
