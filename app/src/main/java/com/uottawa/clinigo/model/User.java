package com.uottawa.clinigo.model;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public User() {
    }

    public User(String id, String email, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Getter for id attribute
     *
     * @return string id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for email attribute
     *
     * @return string email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for firstName attribute
     *
     * @return string firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for lastName attribute
     *
     * @return string lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for role attribute
     *
     * @return string role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter for role attribute
     *
     * @param role string to be assigned
     */
    public void setRole(String role) {
        this.role = role;
    }
}
