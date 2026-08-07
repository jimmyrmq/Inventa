package com.djm.inventa.util;

import java.awt.Color;
import java.util.Random;

public class ColorUtil {

    public static String generarRGB() {
        Random random = new Random();

        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return r + " " + g + " " + b;
    }

    public static Color getColor(String color) {

        if(color == null)
            return null;

        String[] rgb = color.split(" ");

        if(rgb.length != 3) {
            return null;
        }

        Integer rValue = getValue(rgb[0]);
        Integer gValue = getValue(rgb[1]);
        Integer bValue = getValue(rgb[2]);

        if (rValue == null || gValue == null || bValue == null) {
            return null;
        }

        int r = rValue;
        int g = gValue;
        int b = bValue;

        return new Color(r, g, b);
    }

    private static Integer getValue(String s) {
        try {
            int value = Integer.parseInt(s);

            if(value < 0 )
                return 0;
            else if(value > 255)
                return 255;

            return value;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
