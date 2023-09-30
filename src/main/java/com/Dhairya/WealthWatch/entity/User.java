package com.Dhairya.WealthWatch.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Column(name="Name",nullable = false,length = 25)
	private String Name;
	
	@Id
	@Column(name="email",nullable = false,length = 30,unique = true)
	private String email;
	
	@Column(name="mobileNumber",nullable = false,length = 10)
	private String mobileNumber;
	
	@Column(name="password",nullable = false)
	private String password;
	
	@Column(name="invested",columnDefinition = "double default 0.0")
	private double invested_value;
	
	@Column(name="current",columnDefinition = "double default 0.0")
	private double current_value;
	
	@Column(name="portfolio_id")
	private List<Long> portfolios_id;
	
	public User(String name, String email, String mobileNumber, String password) {
		super();
		Name = name;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.portfolios_id = new ArrayList<>();
	}

	public double getInvested() {
		return invested_value;
	}

	public void setInvested(double invested) {
		this.invested_value = invested;
	}

	public double getCurrent() {
		return current_value;
	}

	public void setCurrent(Double current) {
		this.current_value = current;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [Name=" + Name + ", email=" + email + ", mobileNumber=" + mobileNumber + ", password=" + password
				+ ", invested=" + invested_value + ", current=" + current_value + "]";
	}

	
	
}