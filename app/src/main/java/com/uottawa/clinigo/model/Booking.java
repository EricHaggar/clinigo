package com.uottawa.clinigo.model;

public class Booking {

    public String address;
    public String clinicName;
    private String date;
    private bookingStatus status;
    private String patientId;
    private String clinicId;

    public Booking() {
    }

    public Booking(String date, String id) {
        this.date = date;
        this.patientId = id;
        this.status = bookingStatus.Active;
    }

    public Booking(String date, String clinicName, String address, String clinicId) {
        this.date = date;
        this.clinicName = clinicName;
        this.address = address;
        this.clinicId = clinicId;
        this.status = bookingStatus.Active;

    }

    public void setStatusToComplete() {
        this.setStatus("Completed");
    }

    public void setStatusToCancel() {
        this.setStatus("Canceled");
    }

    public String getStatus() {
        return this.status.toString();
    }

    public void setStatus(String status) {
        this.status = bookingStatus.valueOf(status);
    }

    public String getDate() {
        return this.date;
    }

    public String getPatientId() {
        return this.patientId;
    }

    public String getClinicName() {
        return this.clinicName;
    }

    public String getClinicAddress() {
        return this.address;
    }

    public String getClinicId() {
        return this.clinicId;
    }

    enum bookingStatus {Canceled, Active, Completed}
}
