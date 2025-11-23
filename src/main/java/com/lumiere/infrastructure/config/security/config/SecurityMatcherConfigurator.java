package com.lumiere.infrastructure.config.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.lumiere.infrastructure.config.api.ApiConfig;
import com.lumiere.presentation.routes.Routes;

public final class SecurityMatcherConfigurator {

    private SecurityMatcherConfigurator() {
    }

    public static void configure(HttpSecurity http) throws Exception {
        final String api = ApiConfig.API_PREFIX;

        http.authorizeHttpRequests(auth -> {

            auth.requestMatchers(
                    // AUTH
                    api + Routes.PUBLIC.AUTH.REGISTER,
                    api + Routes.PUBLIC.AUTH.LOGIN,

                    // PRODUCTS
                    api + Routes.PUBLIC.PRODUCTS.BASE + "/**")
                    .permitAll();
            auth.requestMatchers(
                    // AUTH
                    api + Routes.PRIVATE.AUTH.LOGOUT,
                    api + Routes.PRIVATE.AUTH.ME,
                    api + Routes.PRIVATE.AUTH.UPDATE,
                    api + Routes.PRIVATE.AUTH.DELETE,

                    // PROFILE
                    api + Routes.PRIVATE.USER.PROFILE,

                    // ADMIN
                    api + Routes.PRIVATE.ADMIN.ADD_PRODUCT,
                    api + Routes.PRIVATE.ADMIN.UPDATE_PRODUCT + "/**",
                    api + Routes.PRIVATE.ADMIN.INCREASE_STOCK + "/**",
                    api + Routes.PRIVATE.ADMIN.DECREASE_STOCK + "/**",
                    api + Routes.PRIVATE.ADMIN.UPDATE_PRICE + "/**",

                    // CART
                    api + Routes.PRIVATE.CART.GET_CART + "/**",
                    api + Routes.PRIVATE.CART.REMOVE_MULTIPLE,
                    api + Routes.PRIVATE.CART.REMOVE_SINGLE,
                    api + Routes.PRIVATE.CART.ADD_MULTIPLE,
                    api + Routes.PRIVATE.CART.ADD_SINGLE);
            auth.anyRequest().authenticated();
        });
    }
}
