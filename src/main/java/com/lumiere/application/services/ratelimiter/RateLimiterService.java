package com.lumiere.application.services.ratelimiter;

public interface RateLimiterService {
    boolean isAllowed(String key);
}
