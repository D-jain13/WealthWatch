package com.Dhairya.WealthWatch.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_account")
public class User {
	
	@Column(nullable = false,length = 25)
	private String full_name;
	
	@Id
	@Column(nullable = false,length = 30,unique = true)
	private String email;
	
	@Column(nullable = false,length = 10)
	private String mobile_number;
	
	@Column(nullable = false)
	private String password;
	
	@Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
	private double total_invested_value;
	
	@Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
	private double total_current_value;
	
	@OneToMany(mappedBy = "user")
	private Set<Portfolio> portfolis;
}