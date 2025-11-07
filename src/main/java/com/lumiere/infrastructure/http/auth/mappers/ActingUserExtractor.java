package com.lumiere.infrastructure.http.auth.mappers;

import com.lumiere.domain.vo.ActingUser;
import com.lumiere.infrastructure.http.auth.token.TokenValidator;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ActingUserExtractor {

    public static ActingUser fromToken(String token) {
        UUID id = TokenValidator.getUserId(token);

        Set<String> rolesList = TokenValidator.getRoles(token);
        Set<String> roles = rolesList.stream().collect(Collectors.toSet());

        Set<String> permissionsList = TokenValidator.getPermissions(token);
        Set<String> permissions = permissionsList.stream().collect(Collectors.toSet());

        return new ActingUser(id, roles, permissions);
    }
}