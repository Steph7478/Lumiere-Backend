package com.lumiere.domain.entities;

import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;

public class User extends BaseEntity {
    private Auth auth;

    public User(UUID id, Auth auth) {
        super(id);
        this.auth = Objects.requireNonNull(auth, "Auth cannot be null");
    }

    // Getters
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
}