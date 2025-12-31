package com.lumiere;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.lumiere.infrastructure.config.environment.DotenvLoader;
import com.lumiere.infrastructure.stripe.config.StripeConfig;
import com.lumiere.infrastructure.stripe.services.StripeEventConstructorService;

@SpringBootTest
class LumiereApplicationTests {
	@MockitoBean
	private StripeConfig stripeConfig;

	@MockitoBean
	private StripeEventConstructorService stripeEventConstructorService;

	@MockitoBean
	private RedissonClient redissonClient;

	@Test
	void contextLoads() {
		DotenvLoader.load("test");
	}

}
