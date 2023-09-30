package com.Dhairya.WealthWatch.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Portfolio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="userEmail",nullable=false)
	private String userEmail;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="invested_value",columnDefinition = "double default 0.0")
	private double invested_value;
	
	@Column(name="current_value",columnDefinition = "double default 0.0")
	private double current_value;
	
	@Column(name="stock_id",unique=true)
	private List<String> stocks;

	public Long getId() {
		return id;
	}
	
	
	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public double getInvested_value() {
		return invested_value;
	}

	public void setInvested_value(Double invested_value) {
		this.invested_value = invested_value;
	}

	public double getCurrent_value() {
		return current_value;
	}

	public void setCurrent_value(Double current_value) {
		this.current_value = current_value;
	}

	public Portfolio(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.stocks = new ArrayList<>();
	}

	public Portfolio() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
