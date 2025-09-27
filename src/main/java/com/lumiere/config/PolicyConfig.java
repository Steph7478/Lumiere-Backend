package com.lumiere.config;

import com.lumiere.domain.policies.AdminPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PolicyConfig {

    @Bean
    public AdminPolicy adminPolicy() {
        return new AdminPolicy();
    }
}
