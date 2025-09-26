package com.lumiere.infrastructure.auth;

import java.util.List;
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
        List<String> roles = TokenValidator.getRoles(refreshToken);
        List<String> permissions = TokenValidator.getPermissions(refreshToken);

        if (userId == null || roles == null) {
            throw new RuntimeException("Invalid token");
        }

        return TokenService.generateAccessToken(userId, roles, permissions);
    }
}
