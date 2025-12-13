package com.lumiere.infrastructure.ratelimiter;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lumiere.application.services.ratelimiter.RateLimiterService;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class RedisRateLimiterAdapter implements RateLimiterService {

    private final ConcurrentMap<String, RRateLimiter> rateLimiters = new ConcurrentHashMap<>();
    private final RedissonClient redissonClient;

    private static final long LIMIT = 5L;
    private static final Duration DURATION = Duration.ofSeconds(10);

    @Autowired
    public RedisRateLimiterAdapter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private RRateLimiter getOrCreateRateLimiter(String key) {
        return rateLimiters.computeIfAbsent(key, k -> {
            RRateLimiter rateLimiter = redissonClient.getRateLimiter("ratelimiter:" + k);

            rateLimiter.trySetRate(
                    RateType.OVERALL,
                    LIMIT,
                    DURATION);

            return rateLimiter;
        });
    }

    @Override
    public boolean isAllowed(String key) {
        RRateLimiter rateLimiter = getOrCreateRateLimiter(key);
        return rateLimiter.tryAcquire(1);
    }
}