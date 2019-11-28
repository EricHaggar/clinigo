package com.uottawa.clinigo.model;

public class ClinicInfo {

    private String name;
    private String phoneNumber;
    private Location location;
    private String description;
    private boolean licensed;
    private float sumOfRatings;
    private int numberOfRatings;

    public ClinicInfo() {
    }

    public ClinicInfo(String name, String phoneNumber, Location location, String description, boolean licensed) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.description = description;
        this.licensed = licensed;
        this.numberOfRatings = 0;
        this.sumOfRatings = 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getLicense() {
        return this.licensed;
    }

    public void setLicense(boolean licensed) {
        this.licensed = licensed;
    }

    public void addRating(double rating) {
        this.sumOfRatings += rating;
        this.numberOfRatings++;
    }

    public int getNumberOfRatings() {
        return this.numberOfRatings;
    }

    public float getSumOfRatings() {
        return this.sumOfRatings;
    }

    public float calculateAverageRating() {
        if (numberOfRatings == 0) {
            return 0;
        }
        return this.sumOfRatings / this.numberOfRatings;
    }
}

