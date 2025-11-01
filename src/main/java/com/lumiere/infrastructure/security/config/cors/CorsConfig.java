package com.lumiere.infrastructure.security.config.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.lumiere.infrastructure.security.constants.Methods;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();

        config.setAllowedOriginPatterns(List.of("http://localhost:*"));

        List<String> allowedMethods = List.of(Methods.GET, Methods.POST, Methods.PUT, Methods.DELETE)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        config.setAllowedMethods(allowedMethods);

        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
