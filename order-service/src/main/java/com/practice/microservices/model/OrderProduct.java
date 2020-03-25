package com.practice.microservices.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8518280724293211459L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	private int quantity;

	public OrderProduct(Product product, int quantity) {

		this.product = product;
		this.quantity = quantity;
	}

	public OrderProduct() {

	}

	public int getQuantity() {
		return quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
