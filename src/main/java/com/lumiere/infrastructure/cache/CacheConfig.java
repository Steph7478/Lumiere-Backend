package com.lumiere.infrastructure.cache;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@Configuration
@SuppressWarnings("null")
public class CacheConfig {

        private static final Duration DEFAULT_TTL = Duration.ofMinutes(15);
        private final ObjectMapper globalMapper;

        public CacheConfig(ObjectMapper globalMapper) {
                this.globalMapper = globalMapper;
        }

        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

                RedisCacheConfiguration defaultCache = createCacheConfig(DEFAULT_TTL);

                Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
                cacheConfigurations.put("authJpa", defaultCache.entryTtl(Duration.ofMinutes(5)));
                cacheConfigurations.put("userJpa", defaultCache.entryTtl(Duration.ofMinutes(10)));
                cacheConfigurations.put("productJpa", defaultCache.entryTtl(Duration.ofMinutes(15)));

                return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(defaultCache)
                                .withInitialCacheConfigurations(cacheConfigurations)
                                .build();
        }

        private RedisCacheConfiguration createCacheConfig(Duration ttl) {
                ObjectMapper mapper = globalMapper.copy();

                BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                                .allowIfBaseType(Object.class)
                                .build();

                mapper.activateDefaultTyping(
                                ptv,
                                ObjectMapper.DefaultTyping.NON_FINAL,
                                JsonTypeInfo.As.PROPERTY);

                GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(mapper);

                return RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(ttl)
                                .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
                                .serializeValuesWith(SerializationPair.fromSerializer(serializer))
                                .disableCachingNullValues();
        }
}
