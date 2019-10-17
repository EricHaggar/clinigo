package com.uottawa.camelclinic.model;

public class Person {

    private String username;
    private String password;
    private String role;
    private String id;

    public Person() {}

    public Person(String username, String password, String id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    /**
     * Getter for role attribute
     * @return string role
     */
    public String getRole() {
        return role;
    }

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

    /**
     * Setter for id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for id
     * @return string id
     */
    public String getId() {
        return id;
    }
}
