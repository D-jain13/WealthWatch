package com.Dhairya.WealthWatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dhairya.WealthWatch.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
	
	Optional<User> findByEmail(String email);
}
