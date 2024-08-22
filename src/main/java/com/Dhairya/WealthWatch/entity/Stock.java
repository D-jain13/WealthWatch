package com.Dhairya.WealthWatch.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stock {
	
	@Id
	@Column(nullable = false, unique = true)
	private String stock_name;

	private double price;

	private double high_price;
	
	private double low_price;
	
	private double change;

	private double change_percent;

	@ManyToMany(mappedBy = "stocks")
	private List<Portfolio> portfolios;
}
