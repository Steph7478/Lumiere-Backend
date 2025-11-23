package com.lumiere.domain.services;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.factories.AuthFactory;
import com.lumiere.infrastructure.config.security.utils.PasswordHasher;

import java.util.Optional;
import java.util.UUID;

public class AuthService {

    private AuthService() {
    }

    public static Auth createAuth(String name, String email, String rawPassword, boolean isAdmin) {
        String hashed = PasswordHasher.hash(rawPassword);
        return AuthFactory.from(name, email, hashed, isAdmin, UUID.randomUUID());
    }

    public static void update(
            Auth auth,
            Optional<String> newName,
            Optional<String> newEmail,
            Optional<String> newRawPassword) {

        Optional<String> newHashedPassword = newRawPassword
                .map(PasswordHasher::hash);
        auth.update(
                newName,
                newEmail,
                newHashedPassword);
    }

    public static boolean checkPassword(Auth auth, String rawPassword) {
        return PasswordHasher.verify(rawPassword, auth.getPasswordHash());
    }
}