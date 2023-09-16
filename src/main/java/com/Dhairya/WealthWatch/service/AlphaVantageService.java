package com.Dhairya.WealthWatch.service;

import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Dhairya.WealthWatch.entity.Stock;
import com.Dhairya.WealthWatch.repository.StockRepo;

@Service
public class AlphaVantageService {

	@Value("${alphavantage.api.key}")
	private String apiKey;

	@Autowired
	private StockRepo stockRepo;

	@Autowired
	private StockConvertor stockConvertor;

	String[] stockSymbols = { "AAPL", "AMZN", "ABBV", "ADBE", "AMD", "BMY", "COST", "CVX", "DIS", "HD", "JNJ", "JPM",
			"KO", "MA", "META", "MSFT", "MRK", "NVDA", "PEP", "PG", "PYPL", "UNH", "UNP", "VZ", "WMT" };

	public void getStockQuote() {
		for (String symbol : stockSymbols) {
			
			String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey="
					+ apiKey;

			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();

			try {
				HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
				if (response.statusCode() == 200) {
					String jsonResponse = response.body();
					Stock stock = StockConvertor.convertJsonToStock(jsonResponse);
					stockRepo.save(stock);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
