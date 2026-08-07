package com.djm.inventa.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberUtils {

    private static final Locale SYSTEM_LOCALE = Locale.getDefault();

    public static String format(BigDecimal value) {
        if(value == null) return "";

        NumberFormat numberFormat = NumberFormat.getNumberInstance();

        numberFormat.setGroupingUsed(true);
        numberFormat.setMaximumFractionDigits(Math.max(value.scale(), 0));
        numberFormat.setMinimumFractionDigits(2);

        return numberFormat.format(value);
    }

    public static String format(String value) {
        if (value == null || !value.trim().isEmpty()) {
            return value;
        }
        try {
            BigDecimal number = new BigDecimal(value);

            NumberFormat format = NumberFormat.getNumberInstance();
            format.setMaximumFractionDigits(2);
            
            return format.format(number);
        }
        catch (NumberFormatException exc){
            System.out.println("Error format number: "+exc.getMessage());
        }

        return "0";
    }

    public static BigDecimal parseBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(SYSTEM_LOCALE);

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##", symbols);
            decimalFormat.setParseBigDecimal(true);

            return (BigDecimal) decimalFormat.parse(value);

        } catch (ParseException e) {
            System.out.println("Error parse number: " + e.getMessage());
        }

        return null;
    }
}
