package com.lumiere.domain.policies;

import com.lumiere.domain.entities.Auth;

public class AdminPolicy {

    public boolean isAllowed(Auth auth) {
        return auth.getIsAdmin();
    }
}
