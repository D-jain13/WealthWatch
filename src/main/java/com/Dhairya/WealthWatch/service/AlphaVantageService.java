package com.Dhairya.WealthWatch.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Dhairya.WealthWatch.modal.StockQuote;

@Service
public class AlphaVantageService {
	
	@Value("${alphavantage.api.key}")
	private String apiKey;
	
	@Autowired
	private final RestTemplate restTemplate;

	@Autowired
	private StockService stockService;
	
	public AlphaVantageService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	String[] stockSymbols = {
			"AAPL","AMZN","ABBV","ADBE","AMD","BMY","COST","CVX",
			"DIS","HD","JNJ","JPM","KO","MA","META","MSFT","MRK",
			"NVDA","PEP","PG","PYPL","UNH","UNP","VZ","WMT"};
	
	public List<StockQuote> getStockQuote() {
		List<StockQuote> stockQuotes = new ArrayList<>();
//		
//		for(String symbol : stockSymbols) {
//			String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+symbol+ "&apikey=" + apiKey;
//	        StockQuote response = restTemplate.getForObject(apiUrl, StockQuote.class);
//	        stockQuotes.add(response);
//		}
		String symbol = stockSymbols[0];
		
		String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+symbol+ "&apikey=" + apiKey;
		StockQuote response = restTemplate.getForObject(apiUrl, StockQuote.class);
		//StockQuote stock = stockService.convertJSONtoStock(apiUrl);
       //	stockQuotes.add(stock);
    	stockQuotes.add(response);
        return stockQuotes;
    }
	
}
