package com.lumiere;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.lumiere.infrastructure.security.config.DotenvLoader;

@SpringBootTest
class LumiereApplicationTests {

	@Test
	void contextLoads() {
		DotenvLoader.load("test");
	}

}
