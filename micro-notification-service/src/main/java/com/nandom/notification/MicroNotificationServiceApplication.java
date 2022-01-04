package com.nandom.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
public class MicroNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroNotificationServiceApplication.class, args);
	}

}
