package com.Dhairya.WealthWatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Dhairya.WealthWatch.entity.User;
import com.Dhairya.WealthWatch.exception.RegistrationException;
import com.Dhairya.WealthWatch.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void registration(User user) throws RegistrationException {
		
		if(userRepo.findByEmail(user.getEmail())!=null) {
			throw new RegistrationException("Email already exists");
		}
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
		
	}
}
