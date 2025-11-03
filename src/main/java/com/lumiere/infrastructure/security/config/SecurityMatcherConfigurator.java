package com.lumiere.infrastructure.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.lumiere.presentation.routes.Routes;

public final class SecurityMatcherConfigurator {

    private SecurityMatcherConfigurator() {
    }

    public static void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> {

            auth.requestMatchers(
                    Routes.PUBLIC.AUTH.REGISTER,
                    Routes.PUBLIC.AUTH.LOGIN).permitAll();

            auth.requestMatchers(
                    Routes.PRIVATE.AUTH.ME,
                    Routes.PRIVATE.USER.PROFILE,
                    Routes.PRIVATE.ADMIN.BASE).hasAnyRole("USER", "ADMIN");

            auth.anyRequest().authenticated();
        });
    }
}
