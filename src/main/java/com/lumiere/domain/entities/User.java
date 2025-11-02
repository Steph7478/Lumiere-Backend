package com.lumiere.domain.entities;

import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;

public class User extends BaseEntity {

    private final UUID id;
    private Auth auth;

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

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    // factory
    public static User from(UUID id, Auth auth) {
        return new User(id, auth);
    }
}
