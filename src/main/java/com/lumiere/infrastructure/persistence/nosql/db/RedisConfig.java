package com.lumiere.infrastructure.persistence.nosql.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import com.lumiere.domain.entities.ProductCategory;

import java.util.UUID;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, ProductCategory> productCategoryRedisTemplate(RedisConnectionFactory cf) {

        RedisTemplate<String, ProductCategory> template = new RedisTemplate<>();
        template.setConnectionFactory(cf);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, String> customStringRedisTemplate(RedisConnectionFactory cf) {

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(cf);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisTemplate<String, UUID> uuidRedisTemplate(RedisConnectionFactory cf) {

        RedisTemplate<String, UUID> template = new RedisTemplate<>();
        template.setConnectionFactory(cf);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<>(UUID.class));
        template.afterPropertiesSet();

        return template;
    }
}