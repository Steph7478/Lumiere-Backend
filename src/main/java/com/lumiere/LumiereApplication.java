package com.lumiere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.lumiere.infrastructure.security.config.DotenvLoader;

@SpringBootApplication
@EnableCaching
public class LumiereApplication {

	public static void main(String[] args) {

		DotenvLoader.load("dev");
		SpringApplication.run(LumiereApplication.class, args);
	}

}
