package com.lumiere.domain.factories;

import java.util.UUID;

import com.lumiere.domain.entities.Auth;

public class AuthFactory {

    public static Auth from(String name, String email, String passwordHash, boolean isAdmin, UUID id) {
        return new Auth(name, email, passwordHash, isAdmin, id);
    }
}