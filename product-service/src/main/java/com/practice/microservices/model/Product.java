package com.practice.microservices.model;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Product name is required.")
	@Basic(optional = false)
	private String name;

	private BigDecimal price;

	private String description;

	public Product(Long id, @NotNull(message = "Product name is required.") String name, BigDecimal price,
			String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
	}

	public Product() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name +"]";
	}
}
