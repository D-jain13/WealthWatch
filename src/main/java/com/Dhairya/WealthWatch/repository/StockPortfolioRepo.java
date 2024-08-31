package com.Dhairya.WealthWatch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dhairya.WealthWatch.entity.StockPortfolio;

@Repository
public interface StockPortfolioRepo extends JpaRepository<StockPortfolio, Integer> {

	Page<StockPortfolio> findByPortfolioId(String portfolioId, Pageable pageable);

}
