package com.uottawa.clinigo.model;

public class Location {

    private String address;
    private String city;
    private String postalCode;
    private String province;
    private String country;

    public Location(String address, String city, String postalCode, String province, String country){

        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
        this.country = country;
    }

    public void setAddress(String address){this.address = address;}

    public void setCity(String city){this.city = city;}

    public void setPostalCode(String postalCode){this.postalCode = postalCode;}

    public void setProvince(String province){this.province = province;}

    public void setCountry(String country){this.country = country;}

    public String getAddress(){return this.address;}

    public String getCity(){return  this.city;}

    public String getPostalCode(){return this.postalCode;}

    public String getProvince(){return this.province;}

    public String getCountry(){return this.country;}

}
