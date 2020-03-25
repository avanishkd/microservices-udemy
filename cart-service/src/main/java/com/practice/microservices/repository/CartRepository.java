package com.practice.microservices.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.microservices.model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

	public Cart findByUserId(long id);

	public void deleteByUserId(long id);

}