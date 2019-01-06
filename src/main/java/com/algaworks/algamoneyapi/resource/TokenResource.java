package com.algaworks.algamoneyapi.resource;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.config.property.AlgamoneyApiProperty;
import com.algaworks.algamoneyapi.model.Usuario;
import com.algaworks.algamoneyapi.repository.UsuarioRepository;

@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AlgamoneyApiProperty algamoneyApiApplication;
	
	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(algamoneyApiApplication.getSeguranca().isEnableHttps()); // perguntamos agora, se esta true ou false
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
		
	}
	
	 @GetMapping("/user/me") // teste para mostrar todos dados do usuario na tela
	    public ResponseEntity<Usuario> getUsuarioAtual(HttpServletRequest req) {
	        Principal principal = req.getUserPrincipal();
	        String usuNome = principal.getName();
	        Optional<Usuario> usuoptional = usuarioRepository.findByEmail(usuNome);
	        Usuario usuario = usuoptional.orElseThrow(() ->new RuntimeException("Info de usuário logado não foram acessíveis."));
	        
	        return ResponseEntity.ok(usuario);
	    }
	
	// Nesta classe é feito o "logout" do usuario
	// nós pegamos o cookie que contem o refresh Token e o excluimos
	

}
