package com.uottawa.clinigo;
import org.junit.BeforeClass;
import org.junit.Test;
import com.uottawa.clinigo.model.ClinicInfo;
import com.uottawa.clinigo.model.Location;
import com.uottawa.clinigo.utilities.ValidationUtilities;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.junit.Assert.*;

public class RatingFunctionalityTesting {

    private static Location address = new Location("123 test", "Ottawa", "k1k1k1", "ON", "Canada");
    private static ClinicInfo tester = new ClinicInfo("test", "6136181921", address, "this a test", true);

    @BeforeClass
    public static  void verifyRatingInitialValue(){
        assertEquals(0.0, tester.calculateAverageRating(), 0);
    }

    @BeforeClass
    public static void checkUnableViewRating(){
        boolean valid = ValidationUtilities.CanViewRating(tester.getNumberOfRatings());
        assertEquals("check unable to view ratings", false, valid);
    }

    @Test
    public void checkRatingFunctionality(){
        tester.addRating(4.2);
        assertEquals(4.2, tester.calculateAverageRating(), 0.01);
        assertEquals(1, tester.getNumberOfRatings(), 0);
        boolean valid = ValidationUtilities.CanViewRating(tester.getNumberOfRatings());
        assertTrue(valid);
    }
}
