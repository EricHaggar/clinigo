package com.uottawa.clinigo.model;

public class ClinicInfo {

    private String name;
    private String phoneNumber;
    private Address address;
    private String description;
    private boolean licensed;

    public ClinicInfo(String name, String phoneNumber, Address address, String description, boolean licensed){

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.licensed = licensed;
    }

    public void setName(String name){this.name = name;}

    public void setPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;}

    public void setAddress(Address address){this.address = address;}

    public void setDescription(String description){this.description = description;}

    public void setLicense(boolean licensed){this.licensed = licensed;}

    public String getName(){return this.name;}

    public String getPhoneNumber(){return this.phoneNumber;}

    public Address getAddress(){return this.address;}

    public String getDescription(){return this.description;}

    public boolean getLicense(){return this.licensed;}
}
