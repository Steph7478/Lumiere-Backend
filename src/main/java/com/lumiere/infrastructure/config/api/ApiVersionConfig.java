package com.lumiere.infrastructure.config.api;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.lumiere.shared.annotations.api.ApiVersion;

@Configuration
@SuppressWarnings("null")
public class ApiVersionConfig implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping() {
            @Override
            protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mappingInfo) {
                var v = AnnotatedElementUtils.findMergedAnnotation(method, ApiVersion.class);
                if (v != null)
                    mappingInfo = RequestMappingInfo.paths(
                            Arrays.stream(v.value()).map(v -> "/api/" + v).toArray(String[]::new)).build()
                            .combine(mappingInfo);

                super.registerHandlerMethod(handler, method, mappingInfo);
            }
        };
    }
}
