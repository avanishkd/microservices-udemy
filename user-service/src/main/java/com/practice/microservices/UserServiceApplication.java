package com.practice.microservices;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

import com.practice.microservices.model.Address;

import com.practice.microservices.model.User;
import com.practice.microservices.model.UserStatus;

import com.practice.microservices.service.UserService;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserService userService) {
		return args -> {
			Address add1=new Address(1, "Bhatewara Nagar", "Pune", "Maharashtra", "India", "411057");
			Address add2=new Address(2, "HInjewadi", "Pimpri Chinchwad", "Maharashtra", "India", "411057");
			userService.saveUser(new User(1L, "Avanish", "1234", add1, UserStatus.LOGOUT.name()));
			userService.saveUser(new User(2L, "Alka", "12345", add2, UserStatus.LOGOUT.name()));
			
		};
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;

	}
}
