package com.lumiere.infrastructure.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.lumiere.presentation.routes.Routes;

public final class SecurityMatcherConfigurator {

    private SecurityMatcherConfigurator() {
    }

    public static void configure(HttpSecurity http) throws Exception {
        final String prefix = ApiConfig.API_PREFIX;

        http.authorizeHttpRequests(auth -> {

            auth.requestMatchers(
                    prefix + Routes.PUBLIC.AUTH.REGISTER,
                    prefix + Routes.PUBLIC.AUTH.LOGIN).permitAll();

            auth.requestMatchers(
                    prefix + Routes.PRIVATE.AUTH.ME,
                    prefix + Routes.PRIVATE.USER.PROFILE,
                    prefix + Routes.PRIVATE.ADMIN.BASE);

            auth.anyRequest().authenticated();
        });
    }
}
