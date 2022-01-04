package com.nandom.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroGatewayServiceApplication.class, args);
		System.out.println("Gateway server is running...");
	}


}

