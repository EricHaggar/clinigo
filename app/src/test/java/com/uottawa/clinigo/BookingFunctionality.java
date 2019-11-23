package com.uottawa.clinigo;

import com.uottawa.clinigo.model.Booking;
import com.uottawa.clinigo.model.Employee;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class BookingFunctionality {

    public static Booking booking1 = new Booking("10/01/2010", "123");
    public static Booking booking2 = new Booking("10/01/2010", "1234");
    public static Employee tester = new Employee("1234","test@test.com", "Marc", "Bastawros");

    @BeforeClass
    public static void verifyActiveBooking(){
        String status = booking1.getStatus();
        assertEquals("check active status", "Active", status);
    }
    @Test
    public void checkBookingStatusUpdate(){
        booking1.setStatusToComplete();
        assertEquals("Completed", booking1.getStatus());
        booking1.setStatusToCancel();
        assertEquals("Canceled", booking1.getStatus());
    }
    @Test
    public void checkAddBooking(){
        tester.addBooking(booking1);
        ArrayList<Booking> bookings = tester.getBookingsByDate(booking1.getDate());
        assertEquals(1,bookings.size(),0);
        tester.addBooking(booking2);
        assertEquals(2, bookings.size(), 0);
        Booking temp = tester.getBookingByPatientId(booking2.getPatientId());
        assertEquals("10/01/2010", temp.getDate());
    }
    @Test
    public void checkWaitingTime(){
        tester.addBooking(booking1);
        assertEquals("check waiting time is 15", 15, tester.getWaitingTime(booking1.getDate()));
        tester.addBooking(booking2);
        assertEquals("check waiting time is 30 mins", 30, tester.getWaitingTime(booking1.getDate()));
    }
    @Test
    public void checkWaitingTimeChange(){
        tester.addBooking(booking1);
        tester.addBooking(booking2);
        booking2.setStatusToCancel();
        assertEquals("check waiting time is now 15 mins", 15, tester.getWaitingTime(booking1.getDate()));
    }
}
