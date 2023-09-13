package com.Dhairya.WealthWatch.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Dhairya.WealthWatch.entity.User;
import com.Dhairya.WealthWatch.exception.LoginException;
import com.Dhairya.WealthWatch.exception.RegistrationException;
import com.Dhairya.WealthWatch.repository.UserRepo;

@Service
public class RegistrationService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void registration(User user) throws RegistrationException {
		
		Optional<User> optionalUser = userRepo.findByEmail(user.getEmail());
		
		if(optionalUser.isPresent()) {
			throw new RegistrationException("Email already exists");
		} 
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
		
	}
	
	public void authentication(User user) throws LoginException{
		if(userRepo.findByEmail(user.getEmail())==null){
			throw new LoginException("Oops!! Looks Like You Do Not Have An Account With Us");
		}
		
		else {
			
		}
	}
}
