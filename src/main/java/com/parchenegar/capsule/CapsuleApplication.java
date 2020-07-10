package com.parchenegar.capsule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CapsuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapsuleApplication.class, args);
	}

}
