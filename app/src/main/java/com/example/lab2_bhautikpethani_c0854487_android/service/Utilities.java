package com.example.lab2_bhautikpethani_c0854487_android.service;

public class Utilities {
    public static boolean isNumeric(String string) {
        Double intValue;

        if(string == null || string.equals("")) {
            return false;
        }

        try {
            intValue = Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {

        }
        return false;
    }
}
