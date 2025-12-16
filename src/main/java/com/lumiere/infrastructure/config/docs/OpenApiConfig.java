package com.lumiere.infrastructure.config.docs;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Lumiere API", version = "1.0", description = "Lumiere API"), security = @SecurityRequirement(name = "cookieAuth"))
@SecurityScheme(name = "cookieAuth", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.COOKIE, paramName = "access_token")
public class OpenApiConfig {

    @Bean
    public OperationCustomizer cookieAuthDescriptionCustomizer() {
        return (operation, handlerMethod) -> {
            if (operation.getSecurity() != null) {
                boolean usesCookieAuth = operation.getSecurity().stream()
                        .anyMatch(sec -> sec.containsKey("cookieAuth"));

                if (usesCookieAuth) {
                    String currentDesc = operation.getDescription() != null ? operation.getDescription() + "\n\n" : "";
                    operation.setDescription(currentDesc + "Requires `access_token` cookie for authentication");
                }
            }
            return operation;
        };
    }
}
