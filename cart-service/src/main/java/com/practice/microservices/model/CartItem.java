package com.practice.microservices.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
public class CartItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	private int quantity;

	public CartItem(Product product, int quantity) {

		this.product = product;
		this.quantity = quantity;
	}

	public CartItem() {

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

	@Transient
	public BigDecimal getSubTotal() {
		return this.getProduct().getPrice().multiply(new BigDecimal(this.getQuantity()));

	}

	/*
	 * @Override public int hashCode() { final int prime = 31; int result = 1;
	 * result = prime * result + ((product == null) ? 0 :
	 * product.getId().intValue());
	 * 
	 * return result; }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (obj == null) return false; if (getClass() != obj.getClass()) return
	 * false; CartItem other = (CartItem) obj; if (product == null) { if
	 * (other.product != null) return false; } else if
	 * (!product.getId().equals(other.product.getId())) return false;
	 * 
	 * return true; }
	 */

}
