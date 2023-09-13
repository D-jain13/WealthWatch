package com.Dhairya.WealthWatch.service;

import org.springframework.stereotype.Component;

import com.Dhairya.WealthWatch.repository.UserRepo;
import com.Dhairya.WealthWatch.entity.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

@Component
public class UserInfoDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = userRepo.findByEmail(username);
		return user.map(UserService::new).orElseThrow(()-> new UsernameNotFoundException("User not found"+username));
	}
	
	
}
