package com.lumiere.security.config.chain;

import com.lumiere.security.filters.JwtAuthenticationFilter;
import com.lumiere.security.config.header.SecurityHeadersConfig;
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

                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                                .csrf(csrf -> csrf.disable())
                                .addFilterBefore(new JwtAuthenticationFilter(),
                                                UsernamePasswordAuthenticationFilter.class)
                                .authorizeHttpRequests(auth -> auth
                                                .anyRequest().authenticated());

                return http.build();
        }
}
