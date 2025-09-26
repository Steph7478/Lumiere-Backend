package com.lumiere.domain.policies;

import com.lumiere.domain.entities.Auth;
import com.lumiere.domain.vo.ActingUser;

public class AdminPolicy {

    public boolean isAdminInJwt(ActingUser user) {
        return user.getRoles().contains("ADMIN");
    }

    public boolean isAdminInEntity(Auth user) {
        return user.getIsAdmin();
    }

    public boolean isAdminFull(ActingUser actingUser, Auth authUser) {
        if (!actingUser.getId().equals(authUser.getId())) {
            return false;
        }
        return isAdminInEntity(authUser) && isAdminInJwt(actingUser);
    }
}
