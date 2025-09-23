package com.lumiere.domain.entity;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID id;
    private final Auth auth;

    private User(UUID id, Auth auth) {
        this.id = id != null ? id : UUID.randomUUID();
        this.auth = Objects.requireNonNull(auth, "Auth cannot be null");
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public Auth getAuth() {
        return auth;
    }

    public String getName() {
        return auth.getName();
    }

    public String getEmail() {
        return auth.getEmail();
    }

    // factory
    public static User from(UUID id, Auth auth) {
        return new User(id, auth);
    }

    public static User createUser(Auth auth) {
        return new User(null, auth);
    }

}
