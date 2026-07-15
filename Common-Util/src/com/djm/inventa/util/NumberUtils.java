package com.djm.inventa.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class NumberUtils {
    
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
}
