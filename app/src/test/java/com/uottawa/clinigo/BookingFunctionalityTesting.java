package com.uottawa.clinigo;

import com.uottawa.clinigo.model.Booking;
import com.uottawa.clinigo.model.ClinicBookings;
import com.uottawa.clinigo.model.Employee;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class BookingFunctionalityTesting {

    @Test
    public void verifyActiveBooking() {
        Booking booking = new Booking("10-01-2010", "123");
        String status = booking.getStatus();
        assertEquals("check active status", "Active", status);
    }

    @Test
    public void verifyAddingFunctionality() {

        Booking booking1 = new Booking("10-01-2010", "123");
        Booking booking2 = new Booking("10-01-2010", "1234");
        Booking booking3 = new Booking("11-01-2010", "12345");
        Employee tester = new Employee("1234", "test@test.com", "Marc", "Bastawros");
        ClinicBookings employeeBookings = new ClinicBookings();
        tester.setClinicBookings(employeeBookings);

        tester.getClinicBookings().addBooking(booking1);
        assertEquals(15, tester.getClinicBookings().getWaitingTime(booking1.getDate()));

        tester.getClinicBookings().addBooking(booking2);
        assertEquals(30, tester.getClinicBookings().getWaitingTime(booking2.getDate()));

        tester.getClinicBookings().addBooking(booking3);
        assertEquals(15, tester.getClinicBookings().getWaitingTime(booking3.getDate()));

        ArrayList<Booking> tempArr = tester.getClinicBookings().getBookingByPatientId(booking1.getPatientId());
        assertEquals(1, tempArr.size());
    }

    @Test
    public void verifyDeletingFunctionality() {

        Booking booking1 = new Booking("10-01-2010", "123");
        Booking booking2 = new Booking("10-01-2010", "1234");
        Booking booking3 = new Booking("11-01-2010", "12345");
        Employee tester = new Employee("1234", "test@test.com", "Marc", "Bastawros");
        ClinicBookings employeeBookings = new ClinicBookings();
        tester.setClinicBookings(employeeBookings);

        tester.getClinicBookings().addBooking(booking1);
        tester.getClinicBookings().addBooking(booking2);
        tester.getClinicBookings().addBooking(booking3);

        tester.getClinicBookings().deleteBookingForPatient(booking1.getPatientId());
        assertEquals(15, tester.getClinicBookings().getWaitingTime(booking1.getDate()));
    }
    @Test
    public void verifyWaitTimeFunctionality(){

        Booking booking1 = new Booking("10-01-2010", "123");
        Booking booking2 = new Booking("10-01-2010", "1234");
        Booking booking3 = new Booking("11-01-2010", "12345");
        Employee tester = new Employee("1234", "test@test.com", "Marc", "Bastawros");
        ClinicBookings employeeBookings = new ClinicBookings();
        tester.setClinicBookings(employeeBookings);

        tester.getClinicBookings().addBooking(booking1);
        tester.getClinicBookings().addBooking(booking2);
        tester.getClinicBookings().addBooking(booking3);

        assertEquals(30, tester.getClinicBookings().getWaitingTime("10-01-2010"));
    }

}
