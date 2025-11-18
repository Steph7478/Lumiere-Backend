package com.lumiere.infrastructure.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvLoader {

    public static void load(String profile) {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env." + profile)
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry -> {
            if (System.getProperty(entry.getKey()) == null) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
        });
    }

    public static void load() {
        String profile = System.getProperty("spring.profiles.active", "dev");
        load(profile);
    }
}
