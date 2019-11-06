package com.uottawa.clinigo.model;

public class Service {

    private String id;
    private String name;
    private String role;

    public Service() {
    }

    public Service (String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Service(String name, String role) {
        this.name = name;
        this.role = role;
    }

    /**
     * Getter for id attribute
     *
     * @return string id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter for name attribute
     *
     * @return string name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for role attribute
     *
     * @return string role
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Setter for id attribute
     *
     * @param id string to be assigned
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter for name attribute
     *
     * @param name string to be assigned
     */
    public void setName(String name) {
        this.name = name;
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
