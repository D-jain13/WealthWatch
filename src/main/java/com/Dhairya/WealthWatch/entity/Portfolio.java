package com.Dhairya.WealthWatch.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Portfolio {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
	private double invested_value;
	
	@Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0.0")
	private double current_value;
	
	@ManyToMany
	@JoinTable(name = "stock_portfolio_table",
				joinColumns = @JoinColumn(name = "portfolio_id"),
				inverseJoinColumns = @JoinColumn(name = "stock_name")
	)
	private List<Stock> stocks;

	@ManyToOne
	@JoinColumn(referencedColumnName = "email",nullable = false)
	private User user;
}
