package com.algaworks.algamoneyapi.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.algaworks.algamoneyapi.config.property.AlgamoneyApiProperty;

// Filtro CORS, para requisições Cross Resource
// CORS: Cross Origin Resource Sharing

@Component	
@Order(Ordered.HIGHEST_PRECEDENCE)	// Alta prioridade
public class CorsFilter implements Filter {
	
	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty; // origem permitida vindo daqui, externamento do profile Algamoney

	@Override	// aqui adicionamos os headers para o funcionamento do CORS
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		resp.setHeader("Access-Control-Allow-Origin", algamoneyApiProperty.getOrigemPermitida());
		resp.setHeader("Access-Control-Allow-Credentials", "true");

		if ("OPTIONS".equals(req.getMethod()) && algamoneyApiProperty.getOrigemPermitida().equals(req.getHeader("Origin"))) {
			
			resp.setHeader("Access-Control-Allow-Methods", "POST,GET,DELETE,PUT,OPTIONS");
			resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			resp.setHeader("Access-Control-Max-Age", "3600");

			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(request, response);
		}
	}

}
