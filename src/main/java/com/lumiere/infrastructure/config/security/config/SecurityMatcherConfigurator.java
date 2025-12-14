package com.lumiere.infrastructure.config.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.context.annotation.Configuration;

import com.lumiere.presentation.routes.Routes;

import java.util.stream.Stream;

@Configuration(proxyBeanMethods = false)
public final class SecurityMatcherConfigurator {

        private SecurityMatcherConfigurator() {
        }

        private static String[] prefixEndpoints(String apiVersionPrefix, String[] endpoints) {
                return Stream.of(endpoints)
                                .map(e -> apiVersionPrefix + e)
                                .toArray(String[]::new);
        }

        public static void configure(HttpSecurity http) throws Exception {
                final String API_VERSION_PREFIX = "/api/v1";

                String[] publicEndpoints = {
                                Routes.PUBLIC.AUTH.REGISTER,
                                Routes.PUBLIC.AUTH.LOGIN,
                                Routes.PUBLIC.PRODUCTS.BASE + "/**",
                                Routes.PUBLIC.PRODUCTS.FILTER + "/**"
                };

                String[] adminEndpoints = {
                                Routes.PRIVATE.ADMIN.BASE + "/**"
                };

                String[] userEndpoints = {
                                Routes.PRIVATE.AUTH.BASE + "/**",
                                Routes.PRIVATE.USER.BASE + "/**",
                                Routes.PRIVATE.CART.BASE + "/**",
                                Routes.PRIVATE.ORDER.BASE + "/**",
                                Routes.PRIVATE.COUPON.BASE + "/**",
                                Routes.PRIVATE.PAYMENT.BASE + "/**",
                                Routes.PRIVATE.WEBHOOKS.STRIPE
                };

                http.authorizeHttpRequests(auth -> {
                        auth.requestMatchers(prefixEndpoints(API_VERSION_PREFIX, publicEndpoints))
                                        .permitAll();

                        auth.requestMatchers(prefixEndpoints(API_VERSION_PREFIX, adminEndpoints))
                                        .hasRole("ADMIN");

                        auth.requestMatchers(prefixEndpoints(API_VERSION_PREFIX, userEndpoints))
                                        .authenticated();

                        auth.anyRequest().denyAll();
                });
        }
}
