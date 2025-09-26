package com.lumiere.security.config.cors;

import com.lumiere.domain.vo.ActingUser;
import com.lumiere.security.config.permissions.RoutePermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("${FRONTEND_URL:http://localhost:*}")
    private String frontendUrl;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOriginPatterns(List.of(frontendUrl));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);

            ActingUser user = (ActingUser) request.getAttribute("actingUser");
            String path = request.getRequestURI();
            List<String> allowedMethods = new ArrayList<>();

            RoutePermissions.ROUTE_PERMISSIONS.forEach((route, rolesMap) -> {
                if (path.startsWith(route)) {
                    if (user != null) {
                        user.getRoles().forEach(role -> rolesMap.getOrDefault(role, List.of())
                                .forEach(m -> allowedMethods.add(m.name())));
                    } else {
                        rolesMap.getOrDefault("PUBLIC", List.of())
                                .forEach(m -> allowedMethods.add(m.name()));
                    }
                }
            });

            config.setAllowedMethods(allowedMethods);
            return config;
        };
    }
}
