package com.Dhairya.WealthWatch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockResponse {
	@JsonProperty("Global Quote")
	private Stock stock;
}
