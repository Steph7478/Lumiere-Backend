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
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;
import com.lumiere.infrastructure.cache.DomainMixins.MoneyMixin;
import com.lumiere.infrastructure.cache.DomainMixins.StockMixin;

@Configuration
@SuppressWarnings("null")
public class CacheConfig {

        private static final Duration DEFAULT_TTL = Duration.ofMinutes(15);
        private final ObjectMapper objectMapper;

        public CacheConfig(ObjectMapper objectMapper) {
                this.objectMapper = objectMapper;
        }

        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
                RedisCacheConfiguration defaultCacheConfig = configureDefaultCache(DEFAULT_TTL, objectMapper);

                Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

                cacheConfigurations.put("authJpa", createCacheConfig(defaultCacheConfig, Duration.ofMinutes(5)));
                cacheConfigurations.put("userJpa", createCacheConfig(defaultCacheConfig, Duration.ofMinutes(10)));

                return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(defaultCacheConfig)
                                .withInitialCacheConfigurations(cacheConfigurations)
                                .build();
        }

        private RedisCacheConfiguration configureDefaultCache(Duration ttl, ObjectMapper objectMapper) {
                ObjectMapper cacheObjectMapper = objectMapper.copy();
                cacheObjectMapper.addMixIn(Money.class, MoneyMixin.class);
                cacheObjectMapper.addMixIn(Stock.class, StockMixin.class);

                BasicPolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                                .allowIfBaseType(Object.class)
                                .build();

                cacheObjectMapper.activateDefaultTyping(
                                ptv,
                                ObjectMapper.DefaultTyping.NON_FINAL,
                                JsonTypeInfo.As.PROPERTY);

                GenericJackson2JsonRedisSerializer jacksonSerializer = new GenericJackson2JsonRedisSerializer(
                                cacheObjectMapper);

                RedisSerializationContext.SerializationPair<Object> valueSerializationPair = SerializationPair
                                .fromSerializer(jacksonSerializer);

                return RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(ttl)
                                .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()))
                                .serializeValuesWith(valueSerializationPair)
                                .disableCachingNullValues();
        }

        private RedisCacheConfiguration createCacheConfig(
                        RedisCacheConfiguration baseConfig,
                        Duration specificTtl) {
                return baseConfig.entryTtl(specificTtl);
        }
}