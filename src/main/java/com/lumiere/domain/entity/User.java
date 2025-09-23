package com.lumiere.domain.entity;

import java.util.UUID;

public class User {

    private final UUID id;
    private Auth auth;

    public User(UUID id, Auth auth) {
        this.id = id == null ? UUID.randomUUID() : id;
        if (auth == null) {
            throw new IllegalArgumentException("Auth cannot be null");
        }
        this.auth = auth;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return auth.getName();
    }

    public String getEmail() {
        return auth.getEmail();
    }
}
