package com.practice.microservices.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.microservices.dto.UserLoginDto;
import com.practice.microservices.dto.UserResponseDto;
import com.practice.microservices.exception.ResourceNotFoundException;
import com.practice.microservices.model.User;
import com.practice.microservices.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private ModelMapper modelMapper;
	private Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PutMapping("/login")
	public ResponseEntity<UserResponseDto> userLogin(@RequestBody UserLoginDto userDto) {
		modelMapper = new ModelMapper();

		User user = userService.login(userDto.getId(), userDto.getPassword());
		if (user == null) {
			logger.error("the requested user with id->{} is not available", userDto.getId());
			throw new ResourceNotFoundException("user with given id->" + userDto.getId() + " not found");
		}
		logger.info("logged user with id->{} and name ->{}", userDto.getId(), user.getName());
		UserResponseDto response = modelMapper.map(user, UserResponseDto.class);

		HttpHeaders headers = new HttpHeaders();
		headers.add("user-available", "yes");
		
		return new ResponseEntity<UserResponseDto>(response,headers,HttpStatus.OK);
	}

	@GetMapping("/userStatus/{userId}")
	public ResponseEntity<UserResponseDto> userStatus(@PathVariable long userId) {

		try {
			UserResponseDto userResponse = userService.getUserById(userId);
			logger.info("user with id->{} and name ->{} found", userResponse.getId(), userResponse.getName());

			HttpHeaders headers = new HttpHeaders();
			headers.add("user-available", "yes");
			return  new ResponseEntity<UserResponseDto>(userResponse,headers,HttpStatus.OK);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("user with id->{} not found", userId);
			throw new ResourceNotFoundException("user with given id->" + userId + " not found");
			
		}
	}

}
