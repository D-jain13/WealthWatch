package com.Dhairya.WealthWatch.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

	private int startIndex = 0;
	private int dailyCount = 0;

	//@Scheduled(cron = "0 0 1 * * ?")
	public void getStockQuoteAutomatically() {
		if (isDayChanged()) {
            dailyCount = 0;
        }

		if (dailyCount < 50) {
			getStockQuote();
		}
	}

	//@Scheduled(fixedDelay = 60000)
	public void getStockQuoteAutomaticallyEvery5Minute() {
		if (dailyCount < 50) {
			getStockQuote();
		}
	}

	private boolean isDayChanged() {
        Calendar calendar = new GregorianCalendar();
        int currentDay = calendar.get(Calendar.DAY_OF_YEAR);

        return currentDay != dailyCount;
    }
	
	String[] stockSymbols = { "AAPL", "AMZN", "ABBV", "ADBE", "AMD", "BMY", "COST", "CVX", "DIS", "HD", "JNJ", "JPM",
			"KO", "MA", "META", "MSFT", "MRK", "NVDA", "PEP", "PG", "PYPL", "UNH", "UNP", "VZ", "WMT" };

	public void getStockQuote() {
		int batchSize = 5; // Define the batch size
		int symbolsToProcess = Math.min(batchSize, stockSymbols.length - startIndex);

		for (int i = startIndex; i < startIndex + symbolsToProcess; i++) {
			if (dailyCount >= 50) {
				break; 
			}

			String symbol = stockSymbols[i];
			String apiUrl = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey="
					+ apiKey;

			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();

			try {
				HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
				if (response.statusCode() == 200) {
					String jsonResponse = response.body();
					Stock stock = StockConvertor.convertJsonToStock(jsonResponse);
					System.out.println(symbol);
					stockRepo.save(stock);

					dailyCount++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		startIndex += batchSize;
		
		
		if(startIndex==49) {
			startIndex=0;
		}
		
	}

}
