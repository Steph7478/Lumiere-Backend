package com.lumiere.security.contexts;

import java.util.Set;
import java.util.UUID;

public class AuthContext {
    private final UUID userId;
    private final Set<String> roles;
    private final Set<String> permissions;

    public AuthContext(UUID userId, Set<String> roles, Set<String> permissions) {
        this.userId = userId;
        this.roles = roles;
        this.permissions = permissions;
    }

    public UUID getUserId() {
        return userId;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
