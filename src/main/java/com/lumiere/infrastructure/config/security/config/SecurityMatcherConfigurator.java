package com.lumiere.infrastructure.config.security.config;

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

        public static final String[] SWAGGER_ENDPOINTS = prefixEndpoints("/api/v1", new String[] {
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/api-docs/**"
        });

        public static final String[] PUBLIC_ENDPOINTS = prefixEndpoints("/api/v1", new String[] {
                        Routes.PUBLIC.AUTH.REGISTER,
                        Routes.PUBLIC.AUTH.LOGIN,
                        Routes.PUBLIC.PRODUCTS.BASE + "/**",
                        Routes.PUBLIC.PRODUCTS.FILTER + "/**",
                        Routes.PUBLIC.WEBHOOKS.STRIPE
        });

        public static final String[] ADMIN_ENDPOINTS = prefixEndpoints("/api/v1", new String[] {
                        Routes.PRIVATE.ADMIN.BASE + "/**"
        });

        public static final String[] USER_ENDPOINTS = prefixEndpoints("/api/v1", new String[] {
                        Routes.PRIVATE.AUTH.BASE + "/**",
                        Routes.PRIVATE.USER.BASE + "/**",
                        Routes.PRIVATE.CART.BASE + "/**",
                        Routes.PRIVATE.ORDER.BASE + "/**",
                        Routes.PRIVATE.COUPON.BASE + "/**",
                        Routes.PRIVATE.PAYMENT.BASE + "/**"
        });
}
