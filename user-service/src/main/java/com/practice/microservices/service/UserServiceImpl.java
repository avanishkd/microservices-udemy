package com.practice.microservices.service;

import java.util.Optional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.microservices.dto.UserResponseDto;
import com.practice.microservices.model.User;
import com.practice.microservices.model.UserStatus;
import com.practice.microservices.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	ModelMapper modelMapper;
	
	private User user;

	@Override
	public User login(@Min(value = 1, message = "Invalid User ID.") long id,
			@NotBlank(message = "Password may not be blank") String password) {

		user=new User();
		user.setId(id);
		user.setPassword(password);
		User getUser=userRepo.findByIdAndPassword(id, password);
		if(getUser!=null) {
			getUser.setStatus(UserStatus.LOGIN.name());
			return userRepo.save(getUser);
		}
		

		return null;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		User userReturn=userRepo.save(user);
		return userReturn;
	}
	
	@Override
	public UserResponseDto getUserById(long id) throws NotFoundException {
		modelMapper=new ModelMapper();
		Optional<User> user=userRepo.findById(id);
		
		
		
		UserResponseDto response = modelMapper.map(user.orElseThrow(() -> new NotFoundException()), UserResponseDto.class);
		return response;
	}

	

}
