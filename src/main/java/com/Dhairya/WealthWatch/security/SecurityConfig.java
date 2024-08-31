package com.Dhairya.WealthWatch.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import com.Dhairya.WealthWatch.service.UserInfoDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@SuppressWarnings("unused")
	private UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
		return new UserInfoDetailsService();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((authorize)-> authorize
				.requestMatchers("/login","/register","/static/**","/css/**").permitAll()
				//.requestMatchers("/**").permitAll()
				.requestMatchers("/user/**")
				.anonymous()
				.anyRequest().authenticated()
				)
			
			.formLogin((form) -> form
				.loginPage("/login")
				.defaultSuccessUrl("/user/dashboard")
				.permitAll()
				)
			
			.logout(logout -> logout
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/login")
	                )
			
			.csrf().disable()
			.cors().disable();
		
		return http.build();
	}
}
