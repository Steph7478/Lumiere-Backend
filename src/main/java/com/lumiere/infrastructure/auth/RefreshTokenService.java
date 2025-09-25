package com.lumiere.infrastructure.auth;

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
        String role = TokenValidator.getRole(refreshToken);

        if (userId == null || role == null) {
            throw new RuntimeException("Invalid token");
        }

        return TokenService.generateAccessToken(userId, role);
    }
}
