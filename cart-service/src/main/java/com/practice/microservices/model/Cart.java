package com.practice.microservices.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Cart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "CART_ID")
	private long id;

	public Cart(Collection<CartItem> items, long userId) {

		this.items = items;
		this.userId = userId;
	}

	@ElementCollection // @OneToMany for basic and embeddables
	@CollectionTable(name = "CartItem") // defaults to "Cart_items" if not overridden
	Collection<CartItem> items;

	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Cart() {

	}

	public Cart(long id) {

		this.id = id;
	}

	public long getId() {
		return id;
	}

	public Collection<CartItem> getItems() {
		return items;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setItems(Collection<CartItem> items) {
		this.items = items;
	}

	@Transient
	public BigDecimal getTotalCartAmount() {
		BigDecimal amount = new BigDecimal("0.0");

		Collection<CartItem> cartItemList = this.getItems();
		for (CartItem itrCartItem : cartItemList) {
			amount = amount.add(itrCartItem.getSubTotal());
		}
		// TODO Auto-generated method stub
		return amount;
	}

}
