package com.Dhairya.WealthWatch.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StockPortfolio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@ManyToOne()
	@JoinColumn(name = "portfolio_id",nullable = false)
	private Portfolio portfolio;
	
	@ManyToOne()
	@JoinColumn(name = "stock_symbol",nullable = false)
	private Stock stock;
	
	@Column(nullable = false)
	private int quantity;
	
}
