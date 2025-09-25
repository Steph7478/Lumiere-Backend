package com.lumiere.domain.policies;

import com.lumiere.domain.entities.Auth;

public class UserPolicy {

    public boolean canPerformAction(Auth auth) {
        return !auth.getIsAdmin();
    }
}
