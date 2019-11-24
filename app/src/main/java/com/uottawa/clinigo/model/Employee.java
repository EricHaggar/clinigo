package com.uottawa.clinigo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Employee extends User {

    private ClinicInfo clinicInfo;
    private WorkingHours workingHours;
    private Map<String, ArrayList<Booking>> bookings = new HashMap<String, ArrayList<Booking>>();

    public Employee() {
        clinicInfo = new ClinicInfo();
        workingHours = new WorkingHours();
    }

    public Employee(String id, String email, String firstName, String lastName) {
        super(id, email, firstName, lastName);
        setRole("Employee");
        //this.workingHours = new WorkingHours();//Initialize Working hours as the default in the beginning
    }

    public void setWorkingHours(WorkingHours workingHours) {this.workingHours = workingHours;}

    public WorkingHours getWorkingHours() {return  this.workingHours;}

    public void setClinicInfo(ClinicInfo info){ this.clinicInfo = info;}

    public ClinicInfo getClinicInfo(){return this.clinicInfo;}

    public void addBooking(Booking booking){
        if(bookings.containsKey(booking.getDate())){
            bookings.get(booking.getDate()).add(booking);
        }
        else {
            System.out.println("Booking :"+booking.getDate());
            bookings.put(booking.getDate(), new ArrayList<Booking>());
            bookings.get(booking.getDate()).add(booking);
        }
    }

    public ArrayList<Booking> getBookingsByDate(String date){return bookings.get(date);}

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
    // returns waiting time of all active bookings in minutes
    public int getWaitingTime(String date){
        int activeBookings = 0;
        ArrayList<Booking> temp = bookings.get(date);
        for(Booking booking: temp){
            if(booking.getStatus().equals("Active")){
                activeBookings++;
            }
        }
        return activeBookings*15;
    }

}
