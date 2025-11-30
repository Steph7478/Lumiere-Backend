package com.lumiere.infrastructure.config.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.lumiere.presentation.routes.Routes;

import java.util.stream.Stream;

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
                                Routes.PRIVATE.ADMIN.ADD_PRODUCT,
                                Routes.PRIVATE.ADMIN.UPDATE_PRODUCT + "/**",
                                Routes.PRIVATE.ADMIN.INCREASE_STOCK + "/**",
                                Routes.PRIVATE.ADMIN.DECREASE_STOCK + "/**",
                                Routes.PRIVATE.ADMIN.UPDATE_PRICE + "/**",
                };

                String[] userEndpoints = {
                                Routes.PRIVATE.AUTH.LOGOUT,
                                Routes.PRIVATE.AUTH.ME,
                                Routes.PRIVATE.AUTH.UPDATE,
                                Routes.PRIVATE.AUTH.DELETE,
                                Routes.PRIVATE.USER.PROFILE,
                                Routes.PRIVATE.ORDER.ORDER_CREATE,
                                Routes.PRIVATE.ORDER.ORDER_ADD,
                                Routes.PRIVATE.ORDER.ORDER_REMOVE,
                                Routes.PRIVATE.ORDER.ORDER_COUPON,
                                Routes.PRIVATE.ORDER.ORDER_CANCEL,
                                Routes.PRIVATE.ORDER.ORDER_GET + "/**",
                                Routes.PRIVATE.CART.DELETE_CART,
                                Routes.PRIVATE.CART.GET_CART + "/**",
                                Routes.PRIVATE.CART.REMOVE_MULTIPLE,
                                Routes.PRIVATE.CART.REMOVE_SINGLE,
                                Routes.PRIVATE.CART.ADD_MULTIPLE,
                                Routes.PRIVATE.CART.ADD_SINGLE
                };

                String[] publicMatchers = prefixEndpoints(API_VERSION_PREFIX, publicEndpoints);
                String[] adminMatchers = prefixEndpoints(API_VERSION_PREFIX, adminEndpoints);
                String[] userMatchers = prefixEndpoints(API_VERSION_PREFIX, userEndpoints);

                http.authorizeHttpRequests(auth -> {
                        auth.requestMatchers(adminMatchers)
                                        .hasRole("ADMIN");

                        auth.requestMatchers(userMatchers)
                                        .authenticated();

                        auth.requestMatchers(publicMatchers)
                                        .permitAll();

                        auth.anyRequest().denyAll();
                });
        }
}