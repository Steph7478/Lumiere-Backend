package com.lumiere.domain.entity;

import java.util.UUID;

public class User {

    private final UUID id;
    private final UUID authId;
    private String name;
    private String email;

    public User(UUID id, UUID authId, String name, String email, Boolean isAdmin) {
        this.authId = authId;
        this.id = id == null ? UUID.randomUUID() : id;
        this.email = email;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getAuthId() {
        return authId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
