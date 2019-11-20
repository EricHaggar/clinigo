package com.uottawa.clinigo.model;

import java.util.ArrayList;

public class Service {

    private String id;
    private String name;
    private String role;
    private ArrayList<String> employees;//List of employee Ids(clinics) that have chosen this service

    public Service() {
    }

    public Service (String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.employees=  new ArrayList<String>();
    }

    public Service(String name, String role) {
        this.name = name;
        this.role = role;
        this.employees=  new ArrayList<String>();
    }

    /**
     * Getter for employess attribute
     *
     * @return ArrayList<String> employees
     */
    public ArrayList<String> getEmployees()
    {
        return this.employees;
    }

    /**
     * Function that adds an employee (clinic) that is using the service
     * */
    public void addEmployee(String employeeId) {
        this.employees.add(employeeId);
    }

    /**
     * Function that initializes an empty array
     * */
    public void initializeEmptyEmployeeArray() {
        this.employees = new ArrayList<String>();
    }

    /**
     * Function that removes an employee Id (clinic) that is no longer using the service
     * */
    public void removeEmployee (String employeeId) {
        this.employees.remove(employeeId);
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
