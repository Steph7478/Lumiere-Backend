package com.lumiere.infrastructure.http.auth.token;

import java.util.Set;
import java.util.UUID;

public class RefreshTokenService {

    public static String refreshToken(String refreshToken) throws Exception {
        if (!TokenValidator.isValid(refreshToken)) {
            throw new RuntimeException("Refresh token invalid");
        }

        if (!TokenValidator.isRefreshToken(refreshToken)) {
            throw new RuntimeException("The token given is not a refresh token");
        }

        UUID userId = TokenValidator.getUserId(refreshToken);
        Set<String> roles = TokenValidator.getRoles(refreshToken);
        Set<String> permissions = TokenValidator.getPermissions(refreshToken);

        if (userId == null || roles == null) {
            throw new RuntimeException("Invalid token");
        }

        return TokenService.generateAccessToken(userId, roles, permissions);
    }
}
