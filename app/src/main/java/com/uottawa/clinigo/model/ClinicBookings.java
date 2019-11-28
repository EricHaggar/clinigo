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

    public ArrayList<Booking> getBookingByPatientId(String patientId){
        ArrayList<Booking> temp = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Booking>> entry : bookings.entrySet()) {
            for (Booking booking : entry.getValue()) {
                if (booking.getPatientId().equals(patientId) && booking.getStatus().equals("Active")) {
                    temp.add(booking);
                }
            }
        }
        return temp;
    }

    public boolean patientHasBookingOnDate(String date, String patientId){
        ArrayList<Booking> patientsBooking = getBookingByPatientId(patientId);
        if(patientsBooking.size() == 0){
            return false;
        }
        else{
            for(int i =0; i < patientsBooking.size(); i++){
                Booking booking = patientsBooking.get(i);
                if(booking.getDate().equals(date)){return true;}
            }
            return false;
        }
    }

    public ArrayList<Booking> getBookingsByDate(String date){
        return this.bookings.get(date);
    }

    public Booking getBookingForPatient(String patientId){

        for(Map.Entry<String, ArrayList<Booking>> entry : bookings.entrySet()){
            ArrayList<Booking> tempArr = entry.getValue();
            for(int i = 0; i < tempArr.size(); i++){
                Booking temp = tempArr.get(i);
                if(temp.getPatientId().equals(patientId)){
                    return this.bookings.get(temp.getDate()).get(i);
                }
            }
        }
        return null;
    }
    /**
     * @param date
     * @return int of waiting time in minutes
     */
    public int getWaitingTime(String date){
        return this.bookings.get(date).size()*15;
    }

    public void deleteBookingForPatient(String patientId){
        boolean found =false;
        for(Map.Entry<String, ArrayList<Booking>> entry : bookings.entrySet()){
            int i = -1;
            for(Booking booking: entry.getValue()){
                i++;
                if(booking.getPatientId().equals(patientId)){
                    found = true;
                    break;
                }
            }
            if(found){entry.getValue().remove(i);}
        }
    }
    public void setBookingsForPatient(ArrayList<Booking> bookings){
        for(int i = 0; i < bookings.size(); i ++){
            this.addBooking(bookings.get(i));
        }
    }
}
