package se.deved.utility;

import java.text.DecimalFormat;

//So that the big numbers displayed in a readable way

public class DoubleFormatHelper {
    public static String formatDouble(Double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }
}
