package com.uottawa.camelclinic.utilities;

import android.text.TextUtils;
import android.widget.EditText;

public class ErrorUtilities {

    public static boolean allInputsFilled(String[] inputs, EditText[] fields) {

        boolean inputsFilled = true;

        for (int i = 0; i < inputs.length; i++) {
            if (TextUtils.isEmpty(inputs[i])) {
                fields[i].setError("Field cannot be empty!");
                inputsFilled = false;
            }
        }
        return inputsFilled;
    }
}
