package com.example.booking_res.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static int getNumber(String string) {
        // Regex get number from string
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            // rutern the first number
            return Integer.parseInt(matcher.group());
        } else {
            // Dont number in string return origin value
            return Integer.MAX_VALUE;
        }
    }
}
