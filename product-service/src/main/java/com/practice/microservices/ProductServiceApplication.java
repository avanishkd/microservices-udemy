package com.practice.microservices;


import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.practice.microservices.model.Product;
import com.practice.microservices.service.ProductService;

import brave.sampler.Sampler;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	
	@Bean
	CommandLineRunner runner(ProductService productService) {
		return args -> {
			productService.save(new Product(1L, "TV Set", new BigDecimal(20000), "LED TV"));
			productService.save(new Product(2L, "Game Console", new BigDecimal(2000), "xbox gaming console"));
			productService.save(new Product(3L, "Sofa", new BigDecimal(5000), "2+2+2 seater sofa"));
			productService.save(new Product(4L, "Icecream", new BigDecimal(5), "butter scotch ice cream"));
			productService.save(new Product(5L, "Beer", new BigDecimal(500), "budwiser beer"));
			productService.save(new Product(6L, "Phone", new BigDecimal(100000), "iphone 11"));
			productService.save(new Product(7L, "Watch", new BigDecimal(20000), "peter england watch"));
		};
	}
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;

	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000");
			}
		};
	}
}
