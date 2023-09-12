package com.Dhairya.WealthWatch.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize)-> authorize
					.requestMatchers("/dashboard/**")
					.anonymous()
					.anyRequest().authenticated())
			.formLogin((form) -> form
				.loginPage("/login")
				.defaultSuccessUrl("/dashboard")
				.permitAll())
			.logout(logout -> logout
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/login") 	
	            )
			 .csrf().disable();
		
		return http.build();
	}
}
