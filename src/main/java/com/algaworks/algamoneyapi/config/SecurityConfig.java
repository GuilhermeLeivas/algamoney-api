package com.algaworks.algamoneyapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(4)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Bean
	@Override	// Aqui é suprido o authManager para o authServerConfig
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	// Está classe esta servindo para suprir o AuthenticationManager
	// que é usado na classe AuthorizationServerConfig
}
