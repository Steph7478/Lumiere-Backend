package com.lumiere.infrastructure.config.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;

@OpenAPIDefinition(info = @Info(title = "Lumiere API", version = "1.0", description = "API Lumiere"), security = @SecurityRequirement(name = "cookieAuth"))
@SecurityScheme(name = "cookieAuth", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.COOKIE, paramName = "jwt")
public class OpenApiConfig {
}
