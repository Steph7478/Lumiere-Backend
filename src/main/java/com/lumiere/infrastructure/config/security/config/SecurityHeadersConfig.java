package com.lumiere.infrastructure.config.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Component
public class SecurityHeadersConfig {

        public void applyHeaders(HttpSecurity http) throws Exception {
                applyContentSecurityPolicy(http);
                applyHsts(http);
                applyFrameOptions(http);
                applyReferrerPolicy(http);
        }

        private void applyContentSecurityPolicy(HttpSecurity http) throws Exception {
                http.headers(headers -> headers
                                .contentSecurityPolicy(csp -> csp
                                                .policyDirectives(
                                                                "default-src 'self'; " +
                                                                                "script-src 'self'; " +
                                                                                "style-src 'self'; " +
                                                                                "img-src 'self' data:; " +
                                                                                "object-src 'none'; " +
                                                                                "base-uri 'self'; " +
                                                                                "frame-ancestors 'none';")));
        }

        private void applyHsts(HttpSecurity http) throws Exception {
                http.headers(headers -> headers
                                .httpStrictTransportSecurity(hsts -> hsts
                                                .includeSubDomains(true)
                                                .preload(true)
                                                .maxAgeInSeconds(31536000)));
        }

        private void applyFrameOptions(HttpSecurity http) throws Exception {
                http.headers(headers -> headers
                                .frameOptions(frame -> frame.deny()));
        }

        private void applyReferrerPolicy(HttpSecurity http) throws Exception {
                http.headers(headers -> headers
                                .referrerPolicy(referrer -> referrer
                                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER)));
        }
}