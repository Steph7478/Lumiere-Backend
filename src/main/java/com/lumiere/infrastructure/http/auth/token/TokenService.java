package com.lumiere.infrastructure.http.auth.token;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.lumiere.infrastructure.http.auth.JWT.SignerProvider;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenService {

    private static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    public static String generateAccessToken(UUID userId, Set<String> roles, Set<String> permissions)
            throws Exception {
        return generateToken(userId, ACCESS_TOKEN_EXPIRATION, "access", roles, permissions);
    }

    public static String generateRefreshToken(UUID userId, Set<String> roles, Set<String> permissions)
            throws Exception {
        return generateToken(userId, REFRESH_TOKEN_EXPIRATION, "refresh", roles, permissions);
    }

    private static String generateToken(UUID userId, long expirationMillis, String type,
            Set<String> roles, Set<String> permissions) throws Exception {

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userId.toString())
                .issuer("Lumière")
                .expirationTime(new Date(System.currentTimeMillis() + expirationMillis))
                .claim("type", type)
                .claim("roles", roles)
                .claim("permissions", permissions)
                .jwtID(UUID.randomUUID().toString())
                .build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .keyID("auth-key-v1")
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(SignerProvider.getSigner());

        return signedJWT.serialize();
    }
}