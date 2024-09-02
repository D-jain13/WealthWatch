package com.Dhairya.WealthWatch.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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

	public List<Stock> getTop5PerformingStocks() {
		return stockRepo.findAll()
				.stream()
				.sorted((s1, s2) -> Double.compare(s2.getChange(), s1.getChange()))
				.limit(5)
				.toList();

	}

	public List<Stock> getTop5LossStocks() {
		return stockRepo.findAll()
				.stream()
				.sorted((s1, s2) -> Double.compare(s1.getChange(), s2.getChange()))
				.limit(5)
				.toList();
	}
}
