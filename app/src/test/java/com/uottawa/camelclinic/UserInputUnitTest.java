package com.uottawa.camelclinic;
import org.junit.Test;
import com.uottawa.camelclinic.utilities.ValidationUtilities;
import static org.junit.Assert.assertEquals;


public class UserInputUnitTest {

    @Test
    public void checkValidFirstName(){
        boolean valid = ValidationUtilities.isValidName("Dude");
        assertEquals("check if name is valid", true, valid);
    }

    @Test
    public void checkInvalidFirstName(){
        boolean valid = ValidationUtilities.isValidName("test@");
        assertEquals("check if name is not valid",false, valid);
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
}