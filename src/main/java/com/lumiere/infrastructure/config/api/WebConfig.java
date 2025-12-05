package com.lumiere.infrastructure.config.api;

import com.lumiere.infrastructure.logging.HttpLoggingInterceptor;
import com.lumiere.presentation.ratelimiter.RateLimitInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HttpLoggingInterceptor loggingInterceptor;
    private RateLimitInterceptor rateLimitInterceptor;

    public WebConfig(HttpLoggingInterceptor loggingInterceptor, RateLimitInterceptor rateLimitInterceptor) {
        this.loggingInterceptor = loggingInterceptor;
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    @SuppressWarnings("null")
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/**");
    }
}
