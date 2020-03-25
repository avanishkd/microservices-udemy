package com.practice.microservices.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class OrderController {
	@Autowired
	OrderRepository orderRepo;

	@PostMapping("/createOrder")
	public Order createUser(@RequestBody Order order) {

		Order flag = orderRepo.save(order);

		return flag;

	}

}
