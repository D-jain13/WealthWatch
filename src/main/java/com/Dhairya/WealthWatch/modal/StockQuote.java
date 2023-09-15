package com.Dhairya.WealthWatch.modal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockQuote {
	
	@JsonProperty("01. symbol")
	private String symbol;
	
	@JsonProperty("05. price")
	private String price;
	
	@JsonProperty("08. previous close")
	private String prevClose;
	
	@JsonProperty("09. change")
	private String change;
	
	@JsonProperty("10. change percent")
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
	
	
}
