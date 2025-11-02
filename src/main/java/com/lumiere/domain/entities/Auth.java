package com.lumiere.domain.entities;

import java.util.Objects;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;

public class Auth extends BaseEntity {

    private final String name;
    private final String email;
    private final String passwordHash;
    private final boolean isAdmin;
    private User user;

    private Auth(String name, String email, String passwordHash, boolean isAdmin, UUID id) {
        super(id);
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash cannot be null");
        this.isAdmin = isAdmin;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Updates imutáveis
    public Auth updateName(String newName) {
        if (Objects.equals(this.name, newName))
            return this;
        return new Auth(newName, this.email, this.passwordHash, this.isAdmin, getId());
    }

    public Auth updateEmail(String newEmail) {
        if (Objects.equals(this.email, newEmail))
            return this;
        return new Auth(this.name, newEmail, this.passwordHash, this.isAdmin, getId());
    }

    public Auth updatePassword(String passwordHash) {
        return new Auth(this.name, this.email, Objects.requireNonNull(passwordHash), this.isAdmin, getId());
    }

    // Factories
    public static Auth hidden(String name, String email) {
        return new Auth(name, email, "***hidden***", false, null);
    }

    public static Auth me(String name, String email, boolean isAdmin) {
        return new Auth(name, email, "***hidden***", isAdmin, null);
    }

    public static Auth from(String name, String email, String passwordHash, boolean isAdmin, UUID id) {
        return new Auth(name, email, passwordHash, isAdmin, id);
    }
}
