package com.lumiere.security.mappers;

import com.lumiere.security.contexts.AuthContext;
import com.lumiere.domain.vo.ActingUser;

public class AuthMapper {

    public static ActingUser toActingUser(AuthContext context) {
        return new ActingUser(
                context.getUserId(),
                context.getRoles(),
                context.getPermissions());
    }
}
