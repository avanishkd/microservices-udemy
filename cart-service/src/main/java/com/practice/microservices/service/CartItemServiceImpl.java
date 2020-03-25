package com.practice.microservices.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.practice.microservices.model.CartItem;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

	
	

	@Override
	public BigDecimal getSubTotal(CartItem cartItem) {
		return cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
		
		
	}

}
