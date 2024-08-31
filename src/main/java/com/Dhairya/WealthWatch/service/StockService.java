package com.Dhairya.WealthWatch.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Dhairya.WealthWatch.entity.Stock;
import com.Dhairya.WealthWatch.repository.StockRepo;

@Service
public class StockService {
	
	@Autowired
	private StockRepo stockRepo;

	public Page<Stock> getAllStocks(Pageable pageable) {
		return stockRepo.findAll(pageable);
	}
	
	public Stock getStockBySymbol(String symbol) {
		Optional<Stock> stock = stockRepo.findBySymbol(symbol);
		return stock.get();
	}
}
