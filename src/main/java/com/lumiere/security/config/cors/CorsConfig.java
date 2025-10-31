package com.lumiere.security.config.cors;

import com.lumiere.security.config.permissions.PermissionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();

        PermissionConfig.ROUTE_PERMISSIONS.forEach((path, rule) -> {
            var config = new CorsConfiguration();
            config.setAllowedOriginPatterns(List.of("http://localhost:*"));
            config.setAllowedMethods(new ArrayList<>(rule.methods()));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);
            source.registerCorsConfiguration(path, config);
        });

        return new CorsFilter(source);
    }
}
