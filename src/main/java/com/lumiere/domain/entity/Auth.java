package com.lumiere.domain.entity;

import java.util.UUID;

public class Auth {

    private final UUID id;
    private String name;
    private String email;
    private String password;
    private boolean isAdmin;

    public Auth(UUID id, String name, String email, String password, boolean isAdmin) {
        if (name == null || email == null || password == null) {
            throw new IllegalArgumentException("name, email and password cannot be null");
        }
        this.id = id != null ? id : UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
