package com.practice.microservices.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.practice.microservices.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByIdAndPassword(Long id, String password);
	

}
