package com.practice.microservices.repository;

import org.springframework.data.repository.CrudRepository;

import com.practice.microservices.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}