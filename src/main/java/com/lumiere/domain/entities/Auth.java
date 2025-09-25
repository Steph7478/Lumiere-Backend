package com.lumiere.domain.entities;

import java.util.Objects;
import java.util.UUID;

public class Auth {

    private final UUID id;
    private final String name;
    private final String email;
    private final String passwordHash;
    private final boolean isAdmin;

    private Auth(UUID id, String name, String email, String passwordHash, boolean isAdmin) {
        this.id = id != null ? id : UUID.randomUUID();
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash cannot be null");
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public Auth updateName(String newName) {
        return new Auth(this.id, Objects.requireNonNull(newName, "name cannot be null"),
                this.email, this.passwordHash, this.isAdmin);
    }

    public Auth updateEmail(String newEmail) {
        return new Auth(this.id, this.name,
                Objects.requireNonNull(newEmail, "email cannot be null"),
                this.passwordHash, this.isAdmin);
    }

    public Auth updatePassword(String passwordHash) {
        return new Auth(this.id, this.name, this.email,
                Objects.requireNonNull(passwordHash, "passwordHash cannot be null"), this.isAdmin);
    }

    public static Auth hidden(String name, String email) {
        return new Auth(UUID.fromString("00000000-0000-0000-0000-000000000000"),
                name,
                email,
                "***hidden***",
                false);
    }

    public static Auth me(String name, String email, UUID id, boolean isAdmin) {
        return new Auth(id,
                name,
                email,
                "***hidden***",
                isAdmin);
    }

    // factory
    public static Auth from(UUID id, String name, String email, String passwordHash, boolean isAdmin) {
        return new Auth(id, name, email, passwordHash, isAdmin);
    }

}
