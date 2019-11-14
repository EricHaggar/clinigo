package com.uottawa.clinigo.utilities;

import androidx.core.util.PatternsCompat;

public class ValidationUtilities {

    public static boolean isValidAdmin(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("5T5ptQ");
    }

    public static boolean isValidEmail(String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidName(String name) {
        return name.matches("[A-Z][a-zA-Z]*") || name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    }

    public static  boolean isValidPostalCode(String postalCode){
        if(postalCode.length() < 6){return false;}
        for(int i = 0; i < postalCode.length(); i++) {
            if(i % 2 == 0) {
                if(!Character.isLetter(postalCode.charAt(i))) {
                    return false;
                }
            } else {
                if(!Character.isDigit(postalCode.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValidPhoneNumber(String phoneNo) {

        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
            //return false if nothing matches the input
        else return false;
    }
}

