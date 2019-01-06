package com.algaworks.algamoneyapi.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.algaworks.algamoneyapi.config.property.AlgamoneyApiProperty;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {
	
	@Autowired
	private AlgamoneyApiProperty algamoneyApiApplication; // Usamos o profile para perguntar se esta true ou false o HTTPS
	
	//Classe responsavel por interceptar o token no corpo de resposta da requisição
	// para que possamos enviar o refresh token para um http cookie

	@Override // verificamos aqui se oq está vindo é realmente um postAccessToken ai
				// executamos o "beforeBodyWrite"
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override // aqui pegamos o valor do refresh token e colocamos dentro do cookie, e tiramos
				// ele do corpo da reposta
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {

		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();

		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;

		String refreshToken = body.getRefreshToken().getValue();
		adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		removerRefreshTokenDoBody(token);

		return body;
	}

	// Método para remover o refresh token do corpo da requisição
	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}

	// Aqui criamos o cookie e adicionamos na resposta
	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {

		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(algamoneyApiApplication.getSeguranca().isEnableHttps()); 
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token"); // para onde enviamos o cookie
		refreshTokenCookie.setMaxAge(2592000); // expiração do cookie
		resp.addCookie(refreshTokenCookie); // adicionamos o cookie no response da requicição

	}

}