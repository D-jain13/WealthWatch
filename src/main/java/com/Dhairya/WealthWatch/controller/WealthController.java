package com.Dhairya.WealthWatch.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.invoker.RequestAttributeArgumentResolver;

import com.Dhairya.WealthWatch.entity.Portfolio;
import com.Dhairya.WealthWatch.entity.Stock;
import com.Dhairya.WealthWatch.entity.User;
import com.Dhairya.WealthWatch.exception.RegistrationException;
import com.Dhairya.WealthWatch.repository.PortfolioRepo;
import com.Dhairya.WealthWatch.repository.StockRepo;
import com.Dhairya.WealthWatch.repository.UserRepo;
import com.Dhairya.WealthWatch.service.AlphaVantageService;
import com.Dhairya.WealthWatch.service.PortfolioService;
import com.Dhairya.WealthWatch.service.RegistrationService;
import com.Dhairya.WealthWatch.service.StockService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class WealthController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private StockService stockService ;
	
	@Autowired
	private PortfolioService portfolioService;
	
	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		String email = getAuthentication();
		
		Optional<User> user = userRepo.findByEmail(email);
		 
		if(user.isPresent()) {
			model.addAttribute("username",user.get().getFull_name());
			model.addAttribute("currentValue",user.get().getTotal_current_value());
			model.addAttribute("investedAmount",user.get().getTotal_invested_value());
		}
		else {
			model.addAttribute("currentValue", 0.0);
            model.addAttribute("investedAmount", 0.0);
		}
	
		return "dashboard";
	}
	
	@GetMapping("/portfolios")
	public String listAllPortfolios(Model model) {
		List<Portfolio> portfolios = portfolioService.getAllPortfolioOfUser(getAuthentication());
		model.addAttribute("portfolios", portfolios);
		return "portfolios";
	}
	
	
	@GetMapping("/stocks")
	public String listAllStock(Model model ) {
		List<Stock> list = stockService.getAllStocks();
		model.addAttribute("Stocks", list);
		return "stock";
	}
	
	@GetMapping("/createPortfolio")
	public String loadCreatePortfolioPage() {
		return "createPortfolio";
	}
	
	@PostMapping("/create-portfolio")
	public String portfolioCreater(Portfolio portfolio) {
		portfolioService.createNewPortfolio(portfolio,getAuthentication());
		return "redirect:/user/dashboard";
	}
	
	@GetMapping("/portfolio/{id}")
	public String showPortfolioDetails(@PathVariable String id,Model model) {
		Portfolio portfolio = portfolioService.getPortfolioDetails(id);
		model.addAttribute("portfolio", portfolio);
		return "showPortfolio";
	}
	
	@RequestMapping("/addStockToPortfolio")
	public String addStockToPortfolio(@RequestParam String portfolioId, @RequestParam String stockSymbol, @RequestParam Integer quantity) {
	    portfolioService.addStockToPortfolio(portfolioId, stockSymbol, quantity);
	    return "redirect:/user/portfolio/" + portfolioId;
	}

	
	@GetMapping("/addStocks")
	public String addStockToPortfolioPage(@RequestParam String portfolioId,Model model) {
		List<Stock> list = stockService.getAllStocks();
		model.addAttribute("Stocks", list);
		model.addAttribute("portfolioId", portfolioId);
		return "addStockToPortfolioPage";
	}
	private String getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	
}
