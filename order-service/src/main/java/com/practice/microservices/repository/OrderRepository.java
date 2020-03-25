package com.practice.microservices.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.microservices.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{

}
