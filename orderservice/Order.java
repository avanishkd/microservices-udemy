package com.practice.microservices.orderservice;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_order")
public class Order {

	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID")
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	  public Set<Product> getProducts() { return products; }
	  
	  public void setProducts(Set<Product> products) { this.products = products; }
	  
	  
	  
	  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	  
	  @JoinTable(name = "TBL_ORDER_PRODUCT", joinColumns = { @JoinColumn(name =
	  "ORDER_ID") }, inverseJoinColumns = {
	  
	  @JoinColumn(name = "PRODUCT_ID") }) private Set<Product> products;
	 
}
