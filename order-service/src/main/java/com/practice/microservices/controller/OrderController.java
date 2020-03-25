package com.practice.microservices.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.practice.microservices.model.Order;
import com.practice.microservices.service.OrderService;

@RestController
public class OrderController {

	private Logger logger = LogManager.getLogger(OrderController.class);
	@Autowired
	OrderService orderService;

	@PostMapping("/placeOrder/{userId}")
	@HystrixCommand(fallbackMethod = "fallBackPlaceOrder")
	public ResponseEntity<Order> placeOrder(@PathVariable long userId) {

		Order order = orderService.placeOrder(userId);
		HttpHeaders headers = new HttpHeaders();
		if (order == null) {

			throw new RuntimeException();
		}

		headers.add("Order-status", "Placed");
		logger.info("order placed successfully with details{}", order);

		return new ResponseEntity<>(order, headers, HttpStatus.CREATED);

	}

	public ResponseEntity<Order> fallBackPlaceOrder(long userId) {

		Order order = new Order();
		order.setDateCreated(LocalDate.now());
		order.setId(0);
		order.setOrderProducts(null);
		order.setUserId(userId);
		order.setStatus("FAILED");
		order.setOrderTotalAmount(BigDecimal.ZERO);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Order-status", "not-Placed");
		logger.warn("unsuccesful order, unable to place ");
		return new ResponseEntity<>(order, headers, HttpStatus.EXPECTATION_FAILED);

	}

}
