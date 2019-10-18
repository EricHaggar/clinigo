package com.uottawa.camelclinic.model;

public class User {

    private String username;
    private String firstName;
    private String lastName;
    private String role;

    public User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Getter for username attribute
     * @return string username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for firstName attribute
     * @return string firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for lastName attribute
     * @return string lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for role attribute
     * @return string role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter for role attribute
     * @param roleAssigned is the role to be set
     */
    public void setRole(String roleAssigned){role = roleAssigned;}
}
