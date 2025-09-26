package com.lumiere.domain.policies;

import com.lumiere.domain.vo.ActingUser;

public class AdminPolicy {

    public boolean isAdmin(ActingUser user) {
        return user.getRoles().contains("ADMIN");
    }

    public boolean canManageProducts(ActingUser user) {
        return user.getPermissions().contains("MANAGE_PRODUCTS");
    }
}
