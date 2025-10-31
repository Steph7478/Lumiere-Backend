package com.lumiere.security.config.chain;

import com.lumiere.security.config.header.SecurityHeadersConfig;
import com.lumiere.security.config.permissions.PermissionConfig;
import com.lumiere.security.filters.JwtAuthorizationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityFilterChainConfig {

        private final SecurityHeadersConfig securityHeadersConfig;
        private final CorsConfigurationSource corsConfigurationSource;

        public SecurityFilterChainConfig(SecurityHeadersConfig securityHeadersConfig,
                        CorsConfigurationSource corsConfigurationSource) {
                this.securityHeadersConfig = securityHeadersConfig;
                this.corsConfigurationSource = corsConfigurationSource;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                securityHeadersConfig.applyHeaders(http);

                http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                                .csrf(csrf -> csrf.disable())
                                .addFilterBefore(new JwtAuthorizationFilter(),
                                                UsernamePasswordAuthenticationFilter.class)
                                .authorizeHttpRequests(auth -> {
                                        PermissionConfig.ROUTE_PERMISSIONS.forEach((route, rule) -> {
                                                if (rule.roles().contains(PermissionConfig.DEFAULT_ROLE)) {
                                                        auth.requestMatchers(route).permitAll();
                                                } else {
                                                        auth.requestMatchers(route).hasAnyAuthority(
                                                                        rule.roles().toArray(String[]::new));
                                                }
                                        });
                                        auth.anyRequest().denyAll();
                                });

                return http.build();
        }
}
