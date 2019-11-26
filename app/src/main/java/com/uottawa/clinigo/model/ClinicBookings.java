package com.uottawa.clinigo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClinicBookings {

    private Map<String, ArrayList<Booking>> bookings;

    public ClinicBookings(){
        this.bookings = new HashMap<>();
    }
    public ClinicBookings(Map<String, ArrayList<Booking>> arr){
        this.bookings = arr;
    }

    public void addBooking(Booking booking){
        if(!bookings.containsKey(booking.getDate())){
            ArrayList<Booking> temp = new ArrayList<>();
            temp.add(booking);
            this.bookings.put(booking.getDate(), temp);
            return;
        }
        ArrayList<Booking> temp = this.bookings.get(booking.getDate());
        temp.add(booking);

    }
    public Booking getBookingByPatientId(String patientId){
        for (Map.Entry<String, ArrayList<Booking>> entry : bookings.entrySet()) {
            for (Booking booking : entry.getValue()) {
                if (booking.getPatientId().equals(patientId) && booking.getStatus().equals("Active")) {
                    return booking;
                }
            }
        }
        return null;
    }

    public ArrayList<Booking> getBookingsByDate(String date){
        return this.bookings.get(date);
    }
    
}
