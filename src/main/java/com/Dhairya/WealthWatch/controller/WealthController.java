package com.Dhairya.WealthWatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Dhairya.WealthWatch.entity.User;
import com.Dhairya.WealthWatch.exception.RegistrationException;
import com.Dhairya.WealthWatch.service.RegistrationService;

@Controller
public class WealthController {
	
	@Autowired
	private RegistrationService registrationService;
	
	
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
	
	@GetMapping("/dashboard")
	public String showDashboard() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "dashboard";
	}
}
