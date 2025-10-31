package com.lumiere.security.config.chain;

import com.lumiere.security.config.header.SecurityHeadersConfig;
import com.lumiere.security.config.permissions.PermissionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
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

                http.cors(cors -> cors.configurationSource(corsConfigurationSource));

                http.csrf(csrf -> csrf.disable());

                http.authorizeHttpRequests(auth -> {
                        PermissionConfig.ROUTE_PERMISSIONS.forEach((route, rule) -> {
                                rule.methods().forEach(method -> {
                                        auth.requestMatchers("/" + route)
                                                        .hasAnyRole(rule.roles().toArray(String[]::new));
                                });
                        });
                        auth.anyRequest().denyAll();
                });

                return http.build();
        }
}
