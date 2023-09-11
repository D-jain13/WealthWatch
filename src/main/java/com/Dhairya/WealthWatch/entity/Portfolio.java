package com.Dhairya.WealthWatch.entity;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class Portfolio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne
	@JoinColumn(name="email")
	private User user;
//	
//	@OneToMany(mappedBy = "portfolio",cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Stock> stocks;
}
