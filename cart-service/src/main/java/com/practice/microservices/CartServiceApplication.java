package com.practice.microservices;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.practice.microservices.repository.CartRepository;
import com.practice.microservices.repository.ProductRepository;

import brave.sampler.Sampler;

@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication
@EnableFeignClients("com.practice.microservices")
@EnableHystrix
public class CartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ProductRepository productRepo, CartRepository cartRepo) {
		return args -> {
			/*
			 * productRepo.save(new Product(1L,"Sofa",new BigDecimal(2000)));
			 * productRepo.save(new Product(2L,"TV",new BigDecimal(5000)));
			 */
			/*
			 * cartRepo.save(new Cart(1)); cartRepo.save(new Cart(2));
			 */
			/*
			 * userRepo.save(new User(1,"Avanish")); userRepo.save(new User(2,"Alka"));
			 */

		};

	}

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;

	}

}
