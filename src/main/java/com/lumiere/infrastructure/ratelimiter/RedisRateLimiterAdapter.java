package com.lumiere.infrastructure.ratelimiter;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lumiere.application.services.ratelimiter.RateLimiterService;
import com.lumiere.infrastructure.config.security.config.SecurityMatcherConfigurator;

import java.time.Duration;
import java.util.Optional;
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
            RRateLimiter limiter = redissonClient.getRateLimiter("ratelimiter:" + k);
            limiter.trySetRate(RateType.OVERALL, LIMIT, DURATION);
            return limiter;
        });
    }

    @Override
    public boolean isAllowed(String key) {
        String uri = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(r -> ((ServletRequestAttributes) r).getRequest().getRequestURI())
                .orElse("");

        for (String endpoint : SecurityMatcherConfigurator.SWAGGER_ENDPOINTS) {
            if (uri.startsWith(endpoint.replace("/**", ""))) {
                return true;
            }
        }

        return getOrCreateRateLimiter(key).tryAcquire(1);
    }
}
