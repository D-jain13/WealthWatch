package com.Dhairya.WealthWatch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
	@Id
	@JsonProperty("01. symbol")
	@Column(name = "symbol",nullable = false,unique = true)
	private String symbol;

	@Column(name = "price")
	@JsonProperty("05. price")
	private String price;

	@JsonProperty("08. previous close")
	@Column(name = "prevClose")
	private String prevClose;

	@JsonProperty("09. change")
	@Column(name = "diff")
	private String change;

	@JsonProperty("10. change percent")
	@Column(name = "changePercent")
	private String changePercent;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(String prevClose) {
		this.prevClose = prevClose;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getChangePercent() {
		return changePercent;
	}

	public void setChangePercent(String changePercent) {
		this.changePercent = changePercent;
	}
	
	public Stock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Stock(String symbol, String price, String prevClose, String change, String changePercent) {
		super();
		this.symbol = symbol;
		this.price = price;
		this.prevClose = prevClose;
		this.change = change;
		this.changePercent = changePercent;
	}

	@Override
	public String toString() {
		return "Stock = symbol=" + symbol + ", price=" + price + ", prevClose=" + prevClose + ", change="
				+ change + ", changePercent=" + changePercent + "]";
	}

	
}
