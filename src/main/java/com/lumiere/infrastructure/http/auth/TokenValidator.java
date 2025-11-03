package com.lumiere.infrastructure.http.auth;

import com.nimbusds.jwt.SignedJWT;
import java.util.List;
import java.util.UUID;
import java.util.Date;

public class TokenValidator {

    public static boolean isValid(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);

            boolean verified = jwt.verify(SignerProvider.getVerifier());

            if (!verified)
                return false;

            Date expiration = jwt.getJWTClaimsSet().getExpirationTime();
            if (expiration == null || !expiration.after(new Date()))
                return false;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
            SignedJWT signedJWT = SignedJWT.parse(token);
            Object claim = signedJWT.getJWTClaimsSet().getClaim(claimKey);
            if (claim instanceof List<?>) {
                return ((List<?>) claim).stream()
                        .map(Object::toString)
                        .toList();
            }
            return List.of();
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
