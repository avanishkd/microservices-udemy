package com.practice.microservices.dto;

import java.math.BigDecimal;
import java.util.Collection;

import com.practice.microservices.model.CartItem;

public class OrderResponseDto {
	
	private long id;
	private long userId;
	Collection<CartItem> items;
	private BigDecimal totalAmount;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Collection<CartItem> getItems() {
		return items;
	}
	public void setItems(Collection<CartItem> items) {
		this.items = items;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	

}
