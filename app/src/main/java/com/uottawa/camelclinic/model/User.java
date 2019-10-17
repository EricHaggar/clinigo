package com.uottawa.camelclinic.model;

public class User {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

    public User() {}

    public User(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Getter for role attribute
     * @return string role
     */
    public String getRole() {
        return role;
    }

    public void setRole(String roleAssigned){role = roleAssigned;}

    /**
     * Getter for username
     * @return string username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for password
     * @return string password
     */
    public String getPassword() {
        return password;
    }

}
