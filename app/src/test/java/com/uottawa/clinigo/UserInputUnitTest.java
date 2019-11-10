package com.uottawa.clinigo;
import org.junit.Test;
import com.uottawa.clinigo.utilities.ValidationUtilities;
import static org.junit.Assert.*;


public class UserInputUnitTest {

    @Test
    public void checkValidFirstName(){
        boolean valid = ValidationUtilities.isValidName("Marc");
        assertEquals("Check if first name is valid", true, valid);
    }

    @Test
    public void checkInvalidFirstName(){
        boolean valid = ValidationUtilities.isValidName("Eric1");
        assertEquals("Check if first name is invalid",false, valid);
    }

    @Test
    public void checkValidLastName() {
        boolean valid = ValidationUtilities.isValidName("Bastawros-Araji");
        assertEquals("Check if last name is valid",true, valid);
    }

    @Test
    public void checkInvalidLastName() {
        boolean valid = ValidationUtilities.isValidName("Bastawros_Araji10");
        assertEquals("Check if last name is invalid",false, valid);
    }

    @Test
    public void checkValidEmail(){
        boolean valid = ValidationUtilities.isValidEmail("test@test.com");
        assertEquals("check if email is valid", true, valid);
    }

    @Test
    public void checkInvalidEmail(){
        boolean valid = ValidationUtilities.isValidEmail("test@testcom");
        assertEquals("check email is not valid", false, valid);
    }

    @Test
    public void checkValidAdmin(){
        boolean valid = ValidationUtilities.isValidAdmin("admin@admin.com", "5T5ptQ");
        assertEquals("check valid admin credentials", true, valid);
    }

    @Test
    public void checkInvalidAdmin(){
        boolean valid = ValidationUtilities.isValidAdmin("admin@gmail.com", "5T5ptQ");
        assertEquals("check invalid admin credentials", false, valid);
    }

    @Test
    public void checkValidService() {
        boolean valid = ValidationUtilities.isValidName("Physiotherapy");
        assertEquals("check valid service name", true, valid);
    }

    @Test
    public void checkInvalidService() {
        boolean valid = ValidationUtilities.isValidName("Physiotherapy2go");
        assertEquals("check invalid service name", false, valid);
    }
}