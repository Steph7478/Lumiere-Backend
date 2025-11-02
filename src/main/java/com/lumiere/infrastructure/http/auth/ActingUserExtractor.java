package com.lumiere.infrastructure.http.auth;

import com.lumiere.domain.vo.ActingUser;
import java.util.Set;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

public class ActingUserExtractor {

    public static ActingUser fromToken(String token) {
        UUID id = TokenValidator.getUserId(token);

        List<String> rolesList = TokenValidator.getRoles(token);
        Set<String> roles = rolesList.stream().collect(Collectors.toSet());

        List<String> permissionsList = TokenValidator.getPermissions(token);
        Set<String> permissions = permissionsList.stream().collect(Collectors.toSet());

        return new ActingUser(id, roles, permissions);
    }
}
