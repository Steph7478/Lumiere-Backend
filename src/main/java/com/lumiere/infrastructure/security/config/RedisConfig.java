package com.lumiere.infrastructure.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.lumiere.domain.entities.ProductCategory;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ProductCategory> productCategoryRedisTemplate(
            RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ProductCategory> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer<ProductCategory> serializer = new Jackson2JsonRedisSerializer<>(
                ProductCategory.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }

}
