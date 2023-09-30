package com.Dhairya.WealthWatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dhairya.WealthWatch.entity.Portfolio;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio, Long> {

	List<Portfolio> findAllByUserEmail(String userEmail);
}
