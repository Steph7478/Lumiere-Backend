package com.lumiere.infrastructure.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.lumiere.presentation.routes.Routes;

public final class SecurityMatcherConfigurator {

    private SecurityMatcherConfigurator() {
    }

    public static void configure(HttpSecurity http) throws Exception {
        final String api = ApiConfig.API_PREFIX;

        http.authorizeHttpRequests(auth -> {

            auth.requestMatchers(
                    api + Routes.PUBLIC.AUTH.REGISTER,
                    api + Routes.PUBLIC.AUTH.LOGIN).permitAll();

            auth.requestMatchers(
                    api + Routes.PRIVATE.AUTH.LOGOUT,
                    api + Routes.PRIVATE.AUTH.ME,
                    api + Routes.PRIVATE.AUTH.UPDATE,
                    api + Routes.PRIVATE.USER.PROFILE,
                    api + Routes.PRIVATE.ADMIN.BASE);

            auth.anyRequest().authenticated();
        });
    }
}
