package com.lumiere.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.lumiere.infrastructure.security.filters.JwtAuthenticationFilter;
import com.lumiere.infrastructure.security.filters.JwtAuthorizationFilter;
import com.lumiere.infrastructure.security.permissions.RoutePermissions;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
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

                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                                .csrf(csrf -> csrf.disable())
                                .addFilterBefore(new JwtAuthenticationFilter(),
                                                UsernamePasswordAuthenticationFilter.class)
                                .addFilterAfter(new JwtAuthorizationFilter(), JwtAuthenticationFilter.class)
                                .authorizeHttpRequests(auth -> {
                                        RoutePermissions.PRIVATE_ROUTES.forEach((path, policy) -> {
                                                policy.methods().forEach(method -> {
                                                        auth.requestMatchers(HttpMethod.valueOf(method.name()), path)
                                                                        .authenticated();
                                                });
                                        });

                                        RoutePermissions.PUBLIC_ROUTES.forEach((path, methods) -> {
                                                methods.forEach(method -> {
                                                        auth.requestMatchers(HttpMethod.valueOf(method.name()), path)
                                                                        .permitAll();
                                                });
                                        });

                                        auth.anyRequest().authenticated();
                                });

                return http.build();
        }
}
