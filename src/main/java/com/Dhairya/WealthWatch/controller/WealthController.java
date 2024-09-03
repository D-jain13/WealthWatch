package com.Dhairya.WealthWatch.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Dhairya.WealthWatch.entity.Portfolio;
import com.Dhairya.WealthWatch.entity.Stock;
import com.Dhairya.WealthWatch.entity.StockPortfolio;
import com.Dhairya.WealthWatch.entity.User;
import com.Dhairya.WealthWatch.repository.UserRepo;
import com.Dhairya.WealthWatch.service.PortfolioService;
import com.Dhairya.WealthWatch.service.StockService;

@Controller
@RequestMapping("/user")
public class WealthController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private StockService stockService;

	@Autowired
	private PortfolioService portfolioService;

	@GetMapping("/test/{id}")
	public String testPage(@PathVariable String id, @RequestParam(defaultValue = "0") int page, // default to first page
			@RequestParam(defaultValue = "3") int size, Model model) {
		Portfolio portfolio = portfolioService.getPortfolioDetails(id);

		// Fetch the paginated stocks for the portfolio
		Page<StockPortfolio> stockPage = portfolioService.getPaginatedStockPortfolios(id, page, size);

		model.addAttribute("portfolio", portfolio);
		model.addAttribute("stockPage", stockPage);
		return "test";
	}

	@GetMapping("/test2")
	public String testPage2(Model model) {

		return "test2";
	}

	@GetMapping("/dashboard")
	public String showDashboard(Model model) {
		String email = getAuthentication();

		Optional<User> user = userRepo.findByEmail(email);

		List<Stock> top5Stocks = stockService.getTop5PerformingStocks();
		List<Stock> top5LossStocks = stockService.getTop5LossStocks();
		
		model.addAttribute("username", user.get().getFull_name());
		model.addAttribute("currentValue", portfolioService.calculateTotalCurrentValueForUser(email));
		model.addAttribute("investedAmount", user.get().getTotal_invested_value());
		model.addAttribute("topPerformingStocks", top5Stocks);
		model.addAttribute("topLossStocks", top5LossStocks);

		return "dashboard";
	}

	@GetMapping("/portfolios")
	public String listAllPortfolios(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "3") int size, Model model) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Portfolio> portfolios = portfolioService.getAllPortfolioOfUser(getAuthentication(), pageable);
		
		for(Portfolio portfolio : portfolios) {
			portfolioService.calculatePortfolioCurrentValue(portfolio.getId());
		}
		
		model.addAttribute("portfolios", portfolios.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", portfolios.getTotalPages());

		return "portfolios";
	}

	@GetMapping("/stocks")
	public String listAllStock(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, Model model) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Stock> stocks = stockService.getAllStocks(pageable);
	
		model.addAttribute("stocks", stocks.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", stocks.getTotalPages());

		return "stock";
	}

	@GetMapping("/createPortfolio")
	public String loadCreatePortfolioPage() {
		return "createPortfolio";
	}

	@PostMapping("/create-portfolio")
	public String portfolioCreater(Portfolio portfolio) {
		portfolioService.createNewPortfolio(portfolio, getAuthentication());
		return "redirect:/user/dashboard";
	}

	@GetMapping("/portfolio/{id}")
	public String showPortfolioDetails(@PathVariable String id, @RequestParam(defaultValue = "0") int page,																			// page
			@RequestParam(defaultValue = "3") int size, Model model) {
		Portfolio portfolio = portfolioService.getPortfolioDetails(id);

		// Fetch the paginated stocks for the portfolio
		Page<StockPortfolio> stockPage = portfolioService.getPaginatedStockPortfolios(id, page, size);

		model.addAttribute("portfolio", portfolio);
		model.addAttribute("stockPage", stockPage);
		return "showPortfolio";
	}

	@RequestMapping("/addStockToPortfolio")
	public String addStockToPortfolio(@RequestParam String portfolioId, @RequestParam String stockSymbol,
			@RequestParam Integer quantity) {
		portfolioService.addStockToPortfolio(portfolioId, stockSymbol, quantity,getAuthentication());
		return "redirect:/user/portfolio/" + portfolioId;
	}

	@GetMapping("/addStocks")
	public String addStockToPortfolioPage(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size, @RequestParam String portfolioId, Model model) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Stock> stocks = stockService.getAllStocks(pageable);
		model.addAttribute("stocks", stocks.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", stocks.getTotalPages());

		model.addAttribute("portfolioId", portfolioId);
		return "addStockToPortfolioPage";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePortfolio(@PathVariable String id, RedirectAttributes redirectAttributes) {
	    portfolioService.deletePortfolio(id,getAuthentication());
	    redirectAttributes.addFlashAttribute("message", "Portfolio deleted successfully.");
	    return "redirect:/user/portfolios";
	}

	private String getAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

}
