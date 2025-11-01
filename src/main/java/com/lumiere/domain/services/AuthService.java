package com.lumiere.domain.services;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.security.utils.PasswordHasher;

public class AuthService {

    private AuthService() {
    }

    public static Auth createAuth(String name, String email, String rawPassword, boolean isAdmin) {
        String hashed = PasswordHasher.hash(rawPassword);
        return Auth.from(null, name, email, hashed, isAdmin);
    }

    public static Auth updatePassword(Auth auth, String newRawPassword) {
        String hashed = PasswordHasher.hash(newRawPassword);
        return auth.updatePassword(hashed);
    }

    public static Auth updateName(Auth auth, String name) {
        return auth.updateName(name);
    }

    public static Auth updateEmail(Auth auth, String email) {
        return auth.updateEmail(email);
    }

    public static boolean checkPassword(Auth auth, String rawPassword) {
        return PasswordHasher.verify(rawPassword, auth.getPasswordHash());
    }
}
