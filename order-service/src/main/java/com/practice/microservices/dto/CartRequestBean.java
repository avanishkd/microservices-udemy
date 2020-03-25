package com.practice.microservices.dto;

import java.math.BigDecimal;
import java.util.Collection;



public class CartRequestBean {
	
	private long id;
	private long userId;
	private Collection<CartItemDto> items;
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
	public Collection<CartItemDto> getItems() {
		return items;
	}
	public void setItems(Collection<CartItemDto> items) {
		this.items = items;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}
