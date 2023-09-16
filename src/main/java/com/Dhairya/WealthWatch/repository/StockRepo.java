package com.Dhairya.WealthWatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dhairya.WealthWatch.entity.Stock;

@Repository
public interface StockRepo extends JpaRepository<Stock, String> {
	
	
}
