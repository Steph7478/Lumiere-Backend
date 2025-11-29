package com.lumiere.presentation.handlers;

import org.springframework.core.MethodParameter;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import com.lumiere.shared.annotations.cache.CacheableResponse;
import java.util.concurrent.TimeUnit;

@ControllerAdvice
@SuppressWarnings("null")
public class CacheControlAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(CacheableResponse.class) ||
                returnType.getContainingClass().isAnnotationPresent(CacheableResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request, ServerHttpResponse response) {

        CacheableResponse annotation = returnType.getMethodAnnotation(CacheableResponse.class);
        if (annotation == null) {
            annotation = returnType.getContainingClass().getAnnotation(CacheableResponse.class);
        }

        if (annotation != null) {
            final long maxAge = annotation.maxAgeInSeconds();
            CacheControl cacheControl;

            if (annotation.noStore()) {
                cacheControl = CacheControl.noStore();
            } else if (maxAge <= 0) {
                cacheControl = CacheControl.noCache();
            } else {
                cacheControl = CacheControl.maxAge(maxAge, TimeUnit.SECONDS);
            }

            cacheControl = cacheControl.noTransform();

            if (annotation.sMaxAgeInSeconds() >= 0) {
                cacheControl = cacheControl.sMaxAge(annotation.sMaxAgeInSeconds(), TimeUnit.SECONDS);
            }

            response.getHeaders().setCacheControl(cacheControl.getHeaderValue());
        }
        return body;
    }
}