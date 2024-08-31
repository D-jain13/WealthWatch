package com.Dhairya.WealthWatch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dhairya.WealthWatch.entity.Stock;
import com.Dhairya.WealthWatch.repository.StockRepo;

@Service
public class StockService {
	
	@Autowired
	private StockRepo stockRepo;

	public List<Stock> getAllStocks() {
		return stockRepo.findAll();
	}
	
	public Stock getStockBySymbol(String symbol) {
		Optional<Stock> stock = stockRepo.findBySymbol(symbol);
		return stock.get();
	}
}
