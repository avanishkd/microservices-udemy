package com.practice.microservices.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.practice.microservices.model.Order;

@Service
@Transactional
public interface OrderService {
	
	public Order placeOrder(long userId);

}
