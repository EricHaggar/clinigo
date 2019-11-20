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

    @Test
    public void checkValidPostalCode(){
        boolean valid = ValidationUtilities.isValidPostalCode("k4a0s5");
        assertEquals("check valid postal code", true, valid);
    }

    @Test
    public void checkInvalidPostalCode(){
        boolean valid = ValidationUtilities.isValidPostalCode("k4a005");
        assertEquals("check invalid postal code", false, valid);
    }

    @Test
    public void checkValidPhoneNumber(){
        boolean valid = ValidationUtilities.isValidPhoneNumber("123-456-4954");
        assertEquals("check valid phone number", true, valid);
    }

    @Test
    public void checkInvalidPhoneNumber(){
        boolean valid = ValidationUtilities.isValidPhoneNumber("123456495");
        assertEquals("check invalid phone number", false, valid);
    }

    @Test
    public void checkValidAddress(){
        boolean valid = false;
        String[] addresses = new String[]{"1234, Queen Street Ouest", "123 Queen Street", "1420 Winterspring"};
        for(int i =0; i < addresses.length; i++){
            valid = ValidationUtilities.isValidAddress(addresses[i]);
            if(valid == false){break;}
        }
        assertEquals("check valid address", true, valid);
    }

    @Test
    public void checkInvalidAddress(){
        boolean valid = true;
        String[] addresses = new String[]{"Street", "Queen West","Street NY 12"};
        for(int i =0; i < addresses.length; i++) {
            valid = ValidationUtilities.isValidAddress(addresses[i]);
            if(valid == true){break;}
        }
        assertEquals("check invalid address", false, valid);
    }
}