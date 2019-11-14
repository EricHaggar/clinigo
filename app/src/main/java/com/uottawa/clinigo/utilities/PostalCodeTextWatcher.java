package com.uottawa.clinigo.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class PostalCodeTextWatcher {

    public PostalCodeTextWatcher(EditText text) {
        text.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() == 3) {
                    s.insert(4, " ");
                }
            }
        });
    }
}
