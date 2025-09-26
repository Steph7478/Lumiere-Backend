package com.lumiere.security.mappers;

import com.lumiere.security.contexts.AuthContext;
import com.lumiere.infrastructure.auth.TokenValidator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AuthMapper {

    public static AuthContext fromToken(String token) {
        UUID userId = TokenValidator.getUserId(token);

        String rolesClaim = TokenValidator.getClaim(token, "roles");
        Set<String> roles = rolesClaim != null ? new HashSet<>(Arrays.asList(rolesClaim.split(","))) : Set.of();

        String permissionsClaim = TokenValidator.getClaim(token, "permissions");
        Set<String> permissions = permissionsClaim != null ? new HashSet<>(Arrays.asList(permissionsClaim.split(",")))
                : Set.of();

        return new AuthContext(userId, roles, permissions);
    }
}
