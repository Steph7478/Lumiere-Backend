package com.lumiere.infrastructure.config.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lumiere.domain.vo.Money;
import com.lumiere.domain.vo.Stock;
import com.lumiere.infrastructure.config.serialization.DomainMixins.MoneyMixin;
import com.lumiere.infrastructure.config.serialization.DomainMixins.StockMixin;

import java.util.Map;

@Configuration
public class JacksonConfig {

    private static final Map<Class<?>, Class<?>> IMMUTABLE_MIXINS = Map.of(
            Money.class, MoneyMixin.class,
            Stock.class, StockMixin.class);

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        IMMUTABLE_MIXINS.forEach(mapper::addMixIn);

        return mapper;
    }
}
