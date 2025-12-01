package com.lumiere.infrastructure.config.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lumiere.domain.vo.CartItem;
import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;
import com.lumiere.infrastructure.config.serialization.DomainMixins.CartItemMixin;
import com.lumiere.infrastructure.config.serialization.DomainMixins.MoneyMixin;
import com.lumiere.infrastructure.config.serialization.DomainMixins.StockMixin;

import java.util.Map;

@Configuration
public class JacksonConfig {

    private static final Map<Class<?>, Class<?>> IMMUTABLE_MIXINS = Map.of(
            Money.class, MoneyMixin.class,
            Stock.class, StockMixin.class,
            CartItem.class, CartItemMixin.class);

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            IMMUTABLE_MIXINS.forEach(builder::mixIn);

            builder.modulesToInstall(new Jdk8Module(), new JavaTimeModule());

            builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                    .featuresToDisable(
                            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        };
    }
}