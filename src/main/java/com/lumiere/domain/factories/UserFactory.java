package com.lumiere.domain.factories;

import java.util.UUID;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.entities.User;

public class UserFactory {

    public static User from(UUID id, Auth auth) {
        return new User(id, auth);
    }

    public static User create(Auth auth) {
        return new User(UUID.randomUUID(), auth);
    }
}