package com.lumiere.infrastructure.http.auth.JWT;

import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;

public class SignerProvider {

    private static final RSAPrivateCrtKey PRIVATE_KEY;
    private static final RSAPublicKey PUBLIC_KEY;

    static {
        try {
            PRIVATE_KEY = KeyLoader.loadPrivateKey("pkey.pem");
            PUBLIC_KEY = KeyLoader.derivePublicKey(PRIVATE_KEY);
        } catch (Exception e) {
            throw new RuntimeException("Error loading key RSA", e);
        }
    }

    public static JWSSigner getSigner() {
        return new RSASSASigner(PRIVATE_KEY);
    }

    public static JWSVerifier getVerifier() {
        return new RSASSAVerifier(PUBLIC_KEY);
    }
}
