package com.uottawa.clinigo.model;

public class Patient extends User {

    private boolean hasBooking;

    public Patient(String id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
        setRole("Patient");
        this.hasBooking = false;
    }

    public void setHasBooking(boolean booking) {
        this.hasBooking = booking;
    }

    public boolean hasBooking() {
        return this.hasBooking;
    }
}
