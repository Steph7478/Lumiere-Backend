package com.lumiere.security.config.cors;

import com.lumiere.security.config.permissions.PermissionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("http://localhost:*"));

        Set<String> allowedMethods = PermissionConfig.ROUTE_PERMISSIONS
                .values()
                .stream()
                .flatMap(rule -> rule.methods().stream())
                .collect(Collectors.toSet());

        config.setAllowedMethods(List.copyOf(allowedMethods));

        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
