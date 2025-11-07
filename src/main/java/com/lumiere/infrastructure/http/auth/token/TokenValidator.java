package com.lumiere.infrastructure.http.auth.token;

import com.lumiere.infrastructure.http.auth.JWT.SignerProvider;
import com.nimbusds.jwt.SignedJWT;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
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

    public static Set<String> getRoles(String token) {
        return getListClaim(token, "roles");
    }

    public static Set<String> getPermissions(String token) {
        return getListClaim(token, "permissions");
    }

    private static Set<String> getListClaim(String token, String claimKey) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            Object claim = signedJWT.getJWTClaimsSet().getClaim(claimKey);

            if (claim instanceof List<?> claimList) {
                return claimList.stream()
                        .map(Object::toString)
                        .collect(Collectors.toSet());
            }
            return Set.of();
        } catch (Exception e) {
            return Set.of();
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
