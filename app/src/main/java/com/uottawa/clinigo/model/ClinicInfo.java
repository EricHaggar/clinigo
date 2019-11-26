package com.uottawa.clinigo.model;

public class ClinicInfo {

    private String name;
    private String phoneNumber;
    private Location location;
    private String description;
    private boolean licensed;
    private double sumOfRatings;
    private int numberOfRatings;

    public ClinicInfo() {
    }

    public ClinicInfo(String name, String phoneNumber, Location location, String description, boolean licensed){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.description = description;
        this.licensed = licensed;
        this.numberOfRatings = 0;
        this.sumOfRatings = 0.0;
    }

    public void setName(String name){this.name = name;}

    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public void setLocation(Location location){this.location = location;}

    public void setDescription(String description){this.description = description;}

    public void setLicense(boolean licensed){this.licensed = licensed;}

    public String getName(){return this.name;}

    public String getPhoneNumber(){return this.phoneNumber;}

    public Location getLocation(){return this.location;}

    public String getDescription(){return this.description;}

    public boolean getLicense(){return this.licensed;}

    public void addRating(double rating){
        this.sumOfRatings += rating;
        this.numberOfRatings ++;
    }

    public int getNumberOfRatings(){return this.numberOfRatings;}

    public double getRating(){
        if(numberOfRatings == 0){return 0.0;}
        return this.sumOfRatings/this.numberOfRatings;
    }
}

