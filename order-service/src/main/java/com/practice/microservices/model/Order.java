package com.practice.microservices.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="tbl_order")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3940425312606174591L;
	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID")
	private long id;

	

	@ElementCollection // @OneToMany for basic and embeddables
	@CollectionTable(name = "OrderProduct") // defaults to "Cart_items" if not overridden
	Collection<OrderProduct> orderProducts;

	private long userId;
	
	private String status;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dateCreated;
	
	private BigDecimal orderTotalAmount;

	public BigDecimal getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Collection<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(Collection<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	

}
