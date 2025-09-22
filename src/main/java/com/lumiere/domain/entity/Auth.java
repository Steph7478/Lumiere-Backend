package com.lumiere.domain.entity;

import java.util.UUID;

public class Auth {

    private final UUID id;
    private String name;
    private String email;
    private String password;
    private boolean isAdmin;

    public Auth(UUID id, String name, String email, String password, Boolean isAdmin) {
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

    public Boolean getisAdmin() {
        return isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setisAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
