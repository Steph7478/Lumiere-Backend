package com.lumiere.domain.vo;

import java.util.Set;
import java.util.UUID;

public class ActingUser {
    private final UUID id;
    private final Set<String> roles;
    private final Set<String> permissions;

    public ActingUser(UUID id, Set<String> roles, Set<String> permissions) {
        this.id = id;
        this.roles = roles;
        this.permissions = permissions;
    }

    public UUID getId() {
        return id;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
