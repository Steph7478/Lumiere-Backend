package com.lumiere.infrastructure.config.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lumiere.infrastructure.config.security.filters.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityFilterChainConfig {

        @Bean
        public SecurityFilterChain swaggerChain(HttpSecurity http) throws Exception {
                http.securityMatcher(SecurityMatcherConfigurator.SWAGGER_ENDPOINTS)
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
                return http.build();
        }

        @Bean
        public SecurityFilterChain publicChain(HttpSecurity http) throws Exception {
                http.securityMatcher(SecurityMatcherConfigurator.PUBLIC_ENDPOINTS)
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
                return http.build();
        }

        @Bean
        public SecurityFilterChain userChain(HttpSecurity http) throws Exception {
                http.securityMatcher(SecurityMatcherConfigurator.USER_ENDPOINTS)
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                                .addFilterBefore(new JwtAuthenticationFilter(),
                                                UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

        @Bean
        public SecurityFilterChain adminChain(HttpSecurity http) throws Exception {
                http.securityMatcher(SecurityMatcherConfigurator.ADMIN_ENDPOINTS)
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth.anyRequest().hasRole("ADMIN"))
                                .addFilterBefore(new JwtAuthenticationFilter(),
                                                UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

}
