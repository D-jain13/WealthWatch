package com.Dhairya.WealthWatch.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
	
	@Id
	@Column(nullable = false, unique = true)
	@JsonProperty("01. symbol")
	private String symbol;
	
	@JsonProperty("05. price")
	private double price;

	@JsonProperty("03. high") 
	private double high_price;
	
	@JsonProperty("04. low")
	private double low_price;
	
	@JsonProperty("09. change")
	private double change;

	@JsonProperty("10. change percent")
	private String change_percent;

	@OneToMany(mappedBy = "stock")
	@JsonIgnore
	private List<StockPortfolio> stockPortfolios;
}
