package se.deved.utility;

import java.text.DecimalFormat;

//Helps big numbers to be displayed in a readable way

public class DoubleFormatHelper {
    public static String formatDouble(Double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }
}
