package com.algaworks.algamoneyapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired	// Injeção feita por causa do SecurityConfig
	private AuthenticationManager authenticationManager;
		
	@Override	// Aqui definimos as configs do CLIENTE, que é aplicação que vai consumir nossa API
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.inMemory()
					.withClient("angular")
					.secret("$2a$10$TpEajS3C1SReTLK/iK0BH.OHhqCkasO9rxEhFG1SkeMwA1lEPA3A6")//{noop}@ngular
					.scopes("read", "write")
					.authorizedGrantTypes("password", "refresh_token") 
					.accessTokenValiditySeconds(1800)
					.refreshTokenValiditySeconds(3600 * 24)
				.and()
					.withClient("mobile")
					.secret("$2a$10$9AIDiMRRDeQROgrKkPLISuJBLz0OCLknPfIi9GGK1bIhqi4lz8.Ca")//{noop}m@bil3
					.scopes("read")
					.authorizedGrantTypes("password", "refresh_token") 
					.accessTokenValiditySeconds(20)
					.refreshTokenValiditySeconds(3600 * 24);
	}	// Com o refresh token, podemos ficar pegando um novo access token novo sempre que um expirar

	
	@Override	// Definimos como vai ser o armazenamento do token e quem vai gerencia-los
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore())
				.accessTokenConverter(accessTokenConverter())
				.reuseRefreshTokens(false)
				.authenticationManager(authenticationManager);
	}
	
	@Bean	// Definimos o access converter que é usado no  método configure, e passamos o "secret" que é algaworks
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");
		
		return accessTokenConverter;
	}

	@Bean	// Definimos o JwtTokenStore para gerenciar o armazenamento do Token
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
}
