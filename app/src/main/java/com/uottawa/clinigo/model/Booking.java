package com.uottawa.clinigo.model;

public class Booking {

    private String date;
    private bookingStatus status;
    private String patiendId;

    enum bookingStatus{Canceled, Active, Completed}

    public Booking(String date, String id){
        this.date = date;
        this.patiendId = id;
        this.status = bookingStatus.Active;
    }

    public void setStatus(String status){
        this.status = bookingStatus.valueOf(status);
    }

    public void setStatusToComplete(){this.setStatus("Completed");}

    public void setStatusToCancel(){this.setStatus("Canceled");}

    public String getStatus(){return this.status.toString();}

    public String getDate(){return this.date;}

    public String getPatientId(){return this.patiendId;}
}
