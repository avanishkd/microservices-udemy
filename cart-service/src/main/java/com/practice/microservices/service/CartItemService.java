package com.practice.microservices.service;

import java.math.BigDecimal;

import com.practice.microservices.model.CartItem;


public interface CartItemService {
	public BigDecimal getSubTotal(CartItem cartItem);

}
