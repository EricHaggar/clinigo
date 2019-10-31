package com.uottawa.camelclinic.model;

public class Service {

    private String id;
    private String name;
    private double hourlyRate;

    public Service (String id, String name, double hourlyRate) {
        this.id = id;
        this.name = name;
        this.hourlyRate = hourlyRate;
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
     * Getter for hourlyRate attribute
     *
     * @return double hourlyRate
     */
    public Double getHourlyRate() {
        return this.hourlyRate;
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
     * Setter for hourlyRate attribute
     *
     * @param hourlyRate double to be assigned
     */
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
