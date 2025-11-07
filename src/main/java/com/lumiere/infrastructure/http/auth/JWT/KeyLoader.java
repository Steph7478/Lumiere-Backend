package com.lumiere.infrastructure.http.auth.JWT;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class KeyLoader {

    public static RSAPrivateCrtKey loadPrivateKey(String path) throws Exception {
        String key = Files.readString(Paths.get(path))
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        RSAPrivateKey privateKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(spec);

        if (!(privateKey instanceof RSAPrivateCrtKey)) {
            throw new RuntimeException("The key is not RSAPrivateCrtKey completed");
        }

        return (RSAPrivateCrtKey) privateKey;
    }

    public static RSAPublicKey derivePublicKey(RSAPrivateCrtKey privateKey) throws Exception {
        RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(
                privateKey.getModulus(),
                privateKey.getPublicExponent());
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(pubSpec);
    }
}
