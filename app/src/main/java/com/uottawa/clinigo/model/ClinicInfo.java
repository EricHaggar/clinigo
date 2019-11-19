package com.uottawa.clinigo.model;

public class ClinicInfo {

    private String name;
    private String phoneNumber;
    private Location location;
    private String description;
    private boolean licensed;

    public ClinicInfo(String name, String phoneNumber, Location location, String description, boolean licensed){

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.description = description;
        this.licensed = licensed;
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
}
