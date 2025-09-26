package com.lumiere.shared.utils;

import java.security.SecureRandom;

public class CouponCodeGenerator {

    private static final char[] CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate() {
        int length = 20 + RANDOM.nextInt(11);
        char[] result = new char[length];

        for (int i = 0; i < length; i++) {
            result[i] = CHARS[RANDOM.nextInt(CHARS.length)];
        }

        return new String(result);
    }
}
