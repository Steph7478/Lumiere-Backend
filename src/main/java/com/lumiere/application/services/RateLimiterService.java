package com.lumiere.application.services;

public interface RateLimiterService {
    boolean isAllowed(String key);
}
