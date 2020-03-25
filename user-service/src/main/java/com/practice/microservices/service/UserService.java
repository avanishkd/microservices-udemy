package com.practice.microservices.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.validation.annotation.Validated;

import com.practice.microservices.dto.UserResponseDto;
import com.practice.microservices.model.User;

@Validated
public interface UserService {
	User login(@Min(value = 1, message = "Invalid User ID.") long id,
			@NotBlank(message = "Password may not be blank") String password);

	User saveUser(User user);

	UserResponseDto getUserById(long id) throws NotFoundException;

}
