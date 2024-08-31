package com.Dhairya.WealthWatch.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Dhairya.WealthWatch.entity.Portfolio;
import com.Dhairya.WealthWatch.entity.Stock;
import com.Dhairya.WealthWatch.entity.StockPortfolio;
import com.Dhairya.WealthWatch.entity.User;
import com.Dhairya.WealthWatch.repository.PortfolioRepo;
import com.Dhairya.WealthWatch.repository.StockPortfolioRepo;
import com.Dhairya.WealthWatch.repository.UserRepo;

@Service
public class PortfolioService {

	@Autowired
	PortfolioRepo portfolioRepo;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	StockPortfolioRepo stockPortfolioRepo;
	
	public Page<Portfolio> getAllPortfolioOfUser(String email,Pageable pageable) {
		return portfolioRepo.findAllByUserEmail(email,pageable);
	}

	public Portfolio getPortfolioDetails(String id) {
		try {
			Portfolio portfolio = portfolioRepo.findById(id).orElseThrow(() -> new Exception("Portfolio not found"));
			return portfolio;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Page<StockPortfolio> getPaginatedStockPortfolios(String portfolioId, int page, int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    return stockPortfolioRepo.findByPortfolioId(portfolioId, pageable);
	}

	public void createNewPortfolio(Portfolio portfolio, String email) {
		Optional<User> user = userRepo.findByEmail(email);
		portfolio.setUser(user.get());
		portfolioRepo.save(portfolio);
	}

	public void addStockToPortfolio(String portfolioId, String stockSymbol, Integer quantity) {
		Optional<Portfolio> portfolio = portfolioRepo.findById(portfolioId);
		
		Stock stock = stockService.getStockBySymbol(stockSymbol);
		
		StockPortfolio stockPortfolio = new StockPortfolio();
		stockPortfolio.setPortfolio(portfolio.get());
		stockPortfolio.setStock(stock);
		stockPortfolio.setQuantity(quantity);
		stockPortfolio.setDate(LocalDate.now());
		Double current_value = portfolio.get().getCurrent_value();
		current_value += stock.getPrice() * quantity;
		
		portfolio.get().setInvested_value(current_value);
		stockPortfolioRepo.save(stockPortfolio);
	}

}
