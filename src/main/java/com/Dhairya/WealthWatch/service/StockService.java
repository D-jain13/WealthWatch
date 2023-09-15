package com.Dhairya.WealthWatch.service;

import org.springframework.stereotype.Service;

import com.Dhairya.WealthWatch.modal.StockQuote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StockService {
	
	private final ObjectMapper objectMapper  = new ObjectMapper();
	
	public StockQuote convertJSONtoStock(String response) {
		try {
			JsonNode jsonNode = objectMapper.readTree(response);
			JsonNode globalQuoteNode = jsonNode.get("Global Quote");
			if(globalQuoteNode!=null) {
				StockQuote stockQuote = new StockQuote();
                stockQuote.setSymbol(globalQuoteNode.get("01. symbol").asText());
                stockQuote.setPrice(globalQuoteNode.get("05. price").asText());
                stockQuote.setPrevClose(globalQuoteNode.get("08. previous close").asText());
                stockQuote.setChange(globalQuoteNode.get("09. change").asText());
                stockQuote.setChangePercent(globalQuoteNode.get("10. change percent").asText());
                
                return stockQuote;
			}
			else {
				return null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
