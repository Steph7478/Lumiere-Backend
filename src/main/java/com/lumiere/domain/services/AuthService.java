package com.lumiere.domain.services;

import com.lumiere.domain.entities.Auth;
import com.lumiere.infrastructure.security.utils.PasswordHasher;

import java.util.UUID;

public class AuthService {

    private AuthService() {
    }

    public static Auth createAuth(String name, String email, String rawPassword, boolean isAdmin) {
        String hashed = PasswordHasher.hash(rawPassword);
        return Auth.from(name, email, hashed, isAdmin, UUID.randomUUID());
    }

    public static Auth updatePassword(Auth auth, String newRawPassword) {
        String hashed = PasswordHasher.hash(newRawPassword);
        return auth.withPasswordHash(hashed);
    }

    public static Auth updateName(Auth auth, String newName) {
        return auth.withName(newName);
    }

    public static Auth updateEmail(Auth auth, String newEmail) {
        return auth.withEmail(newEmail);
    }

    public static boolean checkPassword(Auth auth, String rawPassword) {
        return PasswordHasher.verify(rawPassword, auth.getPasswordHash());
    }
}
