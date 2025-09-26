package com.lumiere.security.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordHasher {

    private PasswordHasher() {
    }

    public static String hash(String rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean verify(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            throw new IllegalArgumentException("Password and hash cannot be null");
        }
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
