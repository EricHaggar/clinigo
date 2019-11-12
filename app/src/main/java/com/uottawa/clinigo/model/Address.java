package com.uottawa.clinigo.model;

public class Address {

    private String streetName;
    private String city;
    private String postalCode;
    private String province;
    private String country;

    public Address(String streetName, String city, String postalCode, String province, String country){

        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.country = country;
    }

    public void setStreetName(String streetName){this.streetName = streetName;}

    public void setCity(String city){this.city = city;}

    public void setPostalCode(String postalCode){this.postalCode = postalCode;}

    public void setProvince(String province){this.province = province;}

    public void setCountry(String country){this.country = country;}

    public String getStreetName(){return this.streetName;}

    public String getCity(){return  this.city;}

    public String getPostalCode(){return this.postalCode;}

    public String getProvince(){return this.province;}

    public String getCountry(){return this.country;}

}
