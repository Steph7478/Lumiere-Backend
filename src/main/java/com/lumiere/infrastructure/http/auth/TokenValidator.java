package com.lumiere.infrastructure.http.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;
import java.util.List;
import java.util.UUID;
import java.util.Date;

public class TokenValidator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

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

    public static List<String> getRoles(String token) {
        return getListClaim(token, "roles");
    }

    public static List<String> getPermissions(String token) {
        return getListClaim(token, "permissions");
    }

    private static List<String> getListClaim(String token, String claimKey) {
        try {
            String json = getClaim(token, claimKey);
            return json != null
                    ? objectMapper.readValue(json, new TypeReference<List<String>>() {
                    })
                    : List.of();
        } catch (Exception e) {
            return List.of();
        }
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
