package com.nandom.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroTransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroTransactionServiceApplication.class, args);
	}

}
