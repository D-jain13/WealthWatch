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
import com.Dhairya.WealthWatch.service.RegistrationService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WealthController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private AlphaVantageService alphaService;
	
	@Autowired
	private StockRepo stockRepo;
	
	@Autowired
	private PortfolioRepo portfolioRepo;
	
	@GetMapping("/")
	public String redirectToLogin() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login() {
		return "redirect:/loginSuccess";
	}
	
	@GetMapping("/register")
	public String showRegister() {
		return "register";
	}
	
	@PostMapping("/loginSuccess")
	public String showLoginSucess() {
		return "loginSuccess";
	}
	
	@PostMapping("/registerSuccess")
	public String showRegisterSucess(User user,Model model) {
		try {
			registrationService.registration(user);
			return "registerSuccess";
		}
		catch (RegistrationException e) {
			model.addAttribute("errorMessage", e.getMessage());	
			return "registrationError";
		}
		
	}
	
	@GetMapping("/user/dashboard")
	public String showDashboard(Model model) {
//		List<StockQuote> stockQuotes = alphaService.getStockQuote();
//		model.addAttribute("stockQuotes", stockQuotes);
		String email = name();
		
		Optional<User> user = userRepo.findByEmail(email);
		
		if(user.isPresent()) {
			model.addAttribute("username",user.get().getName());
			model.addAttribute("currentValue",user.get().getCurrent());
			model.addAttribute("investedAmount",user.get().getInvested());
		}
		else {
			model.addAttribute("currentValue", 0.0);
            model.addAttribute("investedAmount", 0.0);
		}
	
		return "dashboard";
	}
	
	@GetMapping("/user/portfolios")
	private String listAllPortfolios(Model model) {
		List<Portfolio> portfolios = portfolioRepo.findAllByUserEmail(name());
		model.addAttribute("portfolios", portfolios);
		return "portfolios";
	}
	
	@GetMapping("/user/stocks")
	private String listAllStock(Model model ) {
		List<Stock> list = stockRepo.findAll();
		model.addAttribute("Stocks", list);
		return "stock";
	}
	
	@GetMapping("/user/createPortfolio")
	private String loadCreatePortfolioPage() {
		return "createPortfolio";
	}
	
	@PostMapping("/user/create-portfolio")
	private String portfolioCreater(Portfolio portfolio) {
		String userEmail = name();
		portfolio.setUserEmail(userEmail);
		portfolioRepo.save(portfolio);
		return "redirect:/user/dashboard";
	}
	
	private String name() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	
}
