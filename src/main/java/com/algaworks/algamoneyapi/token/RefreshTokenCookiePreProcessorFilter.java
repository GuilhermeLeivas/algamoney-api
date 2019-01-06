package com.algaworks.algamoneyapi.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component	
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {
	
	// Classe para pegar o RefreshToken do cookie e colocar na requisição novamente
	// Assim podemos pegar sempre um novo accessToken sem ter que passar o refreshToken pelo body
	// e o javaScript nao tem acesso a ele, pois o cookie é HttpOnly

	@Override 
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request; // Cast
		
		if("/oauth/token".equalsIgnoreCase(req.getRequestURI()) 
				&& "refresh_token".equals(req.getParameter("grant_type"))
				&& req.getCookies() != null) {
			for(Cookie cookie : req.getCookies()) {
				if(cookie.getName().equals("refreshToken")) {
					String refreshToken = cookie.getValue();
					req =  new MyServletRequestWrapper(req, refreshToken);
				}
			}
			
		}
		chain.doFilter(req, response);
		// Verificamos 3 coisas, se a URI da requisição ta certa, se temos um parametro "grant_type"
		// e se tem mesmo cookies nesta requisição
		// se tivermos, fazemos um loop para pegar cada cookie que tenha o nome de refreshToken e assim
		// pegar o valor dele, e por ultimo precisamos colocar esse valor na requisição, mas como ela ja esta
		// em andamento precisamos usar uma classe auxiliar, que foi a MyServletRequestWrapper.
		
	}
	
	// Classe auxilar para adicionar o valor do cookie na requisição
	static class MyServletRequestWrapper extends HttpServletRequestWrapper {
		
		private String refreshToken;

		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;	
		}
		
		@Override	// Aqui colocamos o cookie dentro da requisição
		public Map<String, String[]> getParameterMap() {
			
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] {refreshToken});
			map.setLocked(true);
			return map;
		}
		
	}

}
