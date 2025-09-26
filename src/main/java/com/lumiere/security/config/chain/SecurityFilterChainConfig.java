package com.lumiere.security.config.chain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.lumiere.security.config.header.SecurityHeadersConfig;
import com.lumiere.security.config.rules.AuthorizationRules;

@Configuration
public class SecurityFilterChainConfig {

        private final SecurityHeadersConfig securityHeadersConfig;

        public SecurityFilterChainConfig(SecurityHeadersConfig securityHeadersConfig) {
                this.securityHeadersConfig = securityHeadersConfig;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http,
                        org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource) throws Exception {

                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> {

                                        AuthorizationRules.publicEndpoints()
                                                        .forEach(ep -> auth.requestMatchers(ep.path()).permitAll());

                                        AuthorizationRules.privateEndpoints()
                                                        .forEach(ep -> auth.requestMatchers(ep.path()).authenticated());

                                        auth.anyRequest().denyAll();
                                });
                securityHeadersConfig.applyHeaders(http);

                return http.build();
        }
}
