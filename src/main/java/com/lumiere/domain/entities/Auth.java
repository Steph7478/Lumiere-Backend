package com.lumiere.domain.entities;

import java.util.Objects;
import java.util.Optional;
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

    // updates
    public Auth update(
            Optional<String> newName,
            Optional<String> newEmail,
            Optional<String> newPasswordHash) {

        String updatedName = newName.orElse(this.name);
        String updatedEmail = newEmail.orElse(this.email);
        String updatedPasswordHash = newPasswordHash.orElse(this.passwordHash);

        if (Objects.equals(this.name, updatedName) &&
                Objects.equals(this.email, updatedEmail) &&
                Objects.equals(this.passwordHash, updatedPasswordHash)) {

            return this;
        }

        return new Auth(
                updatedName,
                updatedEmail,
                updatedPasswordHash,
                this.isAdmin(),
                this.getId());
    }

    public Auth withPasswordHash(String newPasswordHash) {
        return new Auth(this.name, this.email, Objects.requireNonNull(newPasswordHash), this.isAdmin, getId());
    }

    // Factory
    public static Auth from(String name, String email, String passwordHash, boolean isAdmin, UUID id) {
        return new Auth(name, email, passwordHash, isAdmin, id);
    }
}
