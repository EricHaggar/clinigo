package com.uottawa.clinigo;

import com.uottawa.clinigo.model.Booking;
import com.uottawa.clinigo.model.ClinicBookings;
import com.uottawa.clinigo.model.Employee;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class BookingFunctionality {

    public static Booking booking1 = new Booking("10-01-2010", "123");
    public static Booking booking2 = new Booking("10-01-2010", "1234");
    public static Booking booking3 = new Booking("11-01-2010", "12345");
    public static Employee tester = new Employee("1234","test@test.com", "Marc", "Bastawros");
    public static ClinicBookings employeeBookings = new ClinicBookings();

    @BeforeClass
    public static void verifyActiveBooking(){
        String status = booking1.getStatus();
        assertEquals("check active status", "Active", status);
        tester.setClinicBookings(employeeBookings);
    }

    @Test
    public void verifyAddingFunctionality(){
        tester.getClinicBookings().addBooking(booking1);
        assertEquals(30, tester.getClinicBookings().getWaitingTime(booking1.getDate()));
        tester.getClinicBookings().addBooking(booking2);
        assertEquals(45, tester.getClinicBookings().getWaitingTime(booking2.getDate()));
        tester.getClinicBookings().addBooking(booking3);
        assertEquals(15, tester.getClinicBookings().getWaitingTime(booking3.getDate()));
        ArrayList<Booking> tempArr = tester.getClinicBookings().getBookingByPatientId(booking1.getPatientId());
        assertEquals(1, tempArr.size());
    }
    @Test
    public void verifyDeletingFunctionality(){
        tester.getClinicBookings().addBooking(booking1);
        tester.getClinicBookings().addBooking(booking2);
        tester.getClinicBookings().addBooking(booking3);
        assertEquals(booking1.getPatientId(), booking1.getPatientId());
        tester.getClinicBookings().deleteBookingForPatient(booking1.getPatientId());
        assertEquals(15,tester.getClinicBookings().getWaitingTime(booking1.getDate()));
    }
}
