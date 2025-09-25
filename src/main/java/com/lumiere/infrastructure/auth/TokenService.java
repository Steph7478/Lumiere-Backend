package com.lumiere.infrastructure.auth;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenService {

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    public static String generateAccessToken(UUID userId, String role) throws Exception {
        return generateToken(userId, ACCESS_TOKEN_EXPIRATION, "access", role);
    }

    public static String generateRefreshToken(UUID userId, String role) throws Exception {
        return generateToken(userId, REFRESH_TOKEN_EXPIRATION, "refresh", role);
    }

    private static String generateToken(UUID userId, long expirationMillis, String type, String role) throws Exception {

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userId.toString())
                .issuer("Lumière")
                .expirationTime(new Date(System.currentTimeMillis() + expirationMillis))
                .claim("type", type)
                .claim("role", role)
                .jwtID(UUID.randomUUID().toString())
                .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID("auth-key-v1")
                .base64URLEncodePayload(true)
                .type(JOSEObjectType.JWT)
                .criticalParams(Set.of("alg", "typ", "kid"))
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        signedJWT.sign(SignerProvider.getSigner());

        return signedJWT.serialize();
    }

    public static boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            return signedJWT.verify(SignerProvider.getVerifier())
                    && signedJWT.getJWTClaimsSet().getExpirationTime().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
