package com.lumiere.infrastructure.jpa;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class UserJpaEntity {

    @Id
    private UUID id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "auth_id", nullable = false)
    private AuthJpaEntity auth;

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return auth != null ? auth.getName() : null;
    }

    public String getEmail() {
        return auth != null ? auth.getEmail() : null;
    }

    // Setters

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAuth(AuthJpaEntity auth) {
        this.auth = auth;
    }
}
