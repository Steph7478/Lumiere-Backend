package com.lumiere.infrastructure.auth;

import java.util.Date;
import java.util.UUID;

import com.nimbusds.jwt.SignedJWT;

public class TokenValidator {

    public static boolean isValid(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            return signedJWT.verify(SignerProvider.getVerifier()) &&
                    signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAccessToken(String token) {
        return "access".equals(getClaim(token, "type"));
    }

    public static boolean isRefreshToken(String token) {
        return "refresh".equals(getClaim(token, "type"));
    }

    public static UUID getUserId(String token) {
        String subject = getClaim(token, "sub");
        return subject != null ? UUID.fromString(subject) : null;
    }

    public static String getRole(String token) {
        return getClaim(token, "role");
    }

    public static String getClaim(String token, String claimKey) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getStringClaim(claimKey);
        } catch (Exception e) {
            return null;
        }
    }
}
