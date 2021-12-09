package com.photoapp.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class SpringCloudApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudApigatewayApplication.class, args);
	}

}
