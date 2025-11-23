package com.lumiere.domain.entities;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import com.lumiere.domain.entities.base.BaseEntity;

public class Auth extends BaseEntity {

    private String name;
    private String email;
    private String passwordHash;
    private final boolean isAdmin;
    private User user;

    public Auth(String name, String email, String passwordHash, boolean isAdmin, UUID id) {
        super(id);
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "passwordHash cannot be null");
        this.isAdmin = isAdmin;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void update(
            Optional<String> newName,
            Optional<String> newEmail,
            Optional<String> newPasswordHash) {

        newName.ifPresent(name -> this.name = name);
        newEmail.ifPresent(email -> this.email = email);
        newPasswordHash.ifPresent(hash -> this.passwordHash = hash);
    }

    public Auth withPasswordHash(String newPasswordHash) {
        return new Auth(this.name, this.email, Objects.requireNonNull(newPasswordHash), this.isAdmin, getId());
    }
}