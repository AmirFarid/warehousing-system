package com.parchenegar.capsule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAuthorizationServer
@EnableResourceServer
public class CapsuleApplication {
	public static void main(String[] args) {
		SpringApplication.run(CapsuleApplication.class, args);
	}
}
