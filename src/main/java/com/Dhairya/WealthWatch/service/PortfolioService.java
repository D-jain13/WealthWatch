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

	public Page<Portfolio> getAllPortfolioOfUser(String email, Pageable pageable) {
		return portfolioRepo.findAllByUserEmail(email, pageable);
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

	public void addStockToPortfolio(String portfolioId, String stockSymbol, Integer quantity,String email) {
		Optional<Portfolio> portfolio = portfolioRepo.findById(portfolioId);

		Stock stock = stockService.getStockBySymbol(stockSymbol);

		StockPortfolio stockPortfolio = new StockPortfolio();
		stockPortfolio.setPortfolio(portfolio.get());
		stockPortfolio.setStock(stock);
		stockPortfolio.setQuantity(quantity);
		stockPortfolio.setDate(LocalDate.now());

		Double current_value = portfolio.get().getCurrent_value();
		current_value += stock.getPrice() * quantity;

		Double invested_value = portfolio.get().getInvested_value();
		invested_value += stock.getPrice() * quantity;

		portfolio.get().setCurrent_value(current_value);
		portfolio.get().setInvested_value(invested_value);

		Optional<User> user = userRepo.findByEmail(email);
		
		Double user_invested_value = user.get().getTotal_invested_value();
		user_invested_value += stock.getPrice() * quantity;
		
		Double user_current_value = user.get().getTotal_current_value();
		user_current_value += stock.getPrice() * quantity;
		
		user.get().setTotal_current_value(user_current_value);
		user.get().setTotal_invested_value(user_invested_value);
		
		userRepo.save(user.get());
		stockPortfolioRepo.save(stockPortfolio);
	}

	public void deletePortfolio(String id,String email) {
		User user = userRepo.findByEmail(email).get();
		
		Portfolio portfolio = portfolioRepo.findById(id).get();
		
		user.setTotal_current_value(user.getTotal_current_value()-portfolio.getCurrent_value());
		user.setTotal_invested_value(user.getTotal_invested_value()-portfolio.getInvested_value());
		portfolioRepo.deleteById(id);
	}
	public void calculatePortfolioCurrentValue(String portfolioId) {
        List<StockPortfolio> stockPortfolios = stockPortfolioRepo.findByPortfolioId(portfolioId);
        double currentValue = 0.0;

        for (StockPortfolio stockPortfolio : stockPortfolios) {
            Stock stock = stockService.getStockBySymbol(stockPortfolio.getStock().getSymbol());
            if (stock != null) {
                currentValue += stock.getPrice() * stockPortfolio.getQuantity();
            }
        }
        Portfolio portfolio = portfolioRepo.findById(portfolioId).get();
        portfolio.setCurrent_value(currentValue);
        portfolioRepo.save(portfolio);
    }
	 public double calculateTotalCurrentValueForUser(String userEmail) {
	        List<Portfolio> portfolios = portfolioRepo.findAllByUserEmail(userEmail);
	        double totalCurrentValue = 0.0;

	        for (Portfolio portfolio : portfolios) {
	            totalCurrentValue += portfolio.getCurrent_value();
	        }

	        return totalCurrentValue;
	    }
}
