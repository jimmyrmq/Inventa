package com.djm.inventa.admin.util;

import java.security.SecureRandom;

public class Utils {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private Utils(){}

    public static String generateAlphanumericCode(int length) {
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom(); // Usar SecureRandom para una mejor aleatoriedad

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            code.append(ALPHA_NUMERIC_STRING.charAt(index));
        }

        return code.toString();
    }

}
