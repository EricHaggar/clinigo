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
}

