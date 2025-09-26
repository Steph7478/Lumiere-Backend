package com.lumiere.domain.policies;

import com.lumiere.security.contexts.AuthContext;

public class AdminPolicy {

    public boolean canManageUsers(AuthContext auth) {
        return auth.hasPermission("MANAGE_PRODUCTS");
    }

    public boolean isAdmin(AuthContext auth) {
        return auth.hasRole("ADMIN");
    }
}
