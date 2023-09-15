package com.Dhairya.WealthWatch.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Dhairya.WealthWatch.entity.User;
import com.Dhairya.WealthWatch.exception.RegistrationException;
import com.Dhairya.WealthWatch.modal.StockQuote;
import com.Dhairya.WealthWatch.repository.UserRepo;
import com.Dhairya.WealthWatch.service.AlphaVantageService;
import com.Dhairya.WealthWatch.service.RegistrationService;

@Controller
public class WealthController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private AlphaVantageService alphaService;
	
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
	
	private String name() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return authentication.getName();
	}
}
