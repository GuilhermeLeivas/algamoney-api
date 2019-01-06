package com.algaworks.algamoneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algamoneyapi.event.RecursoCriadoEvent;
import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.repository.CategoriaRepository;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository cr;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public List<Categoria> listar() {
		return cr.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = cr.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigo()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@GetMapping(value = "{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('READ')")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Categoria> categoriaRetorno = cr.findById(codigo);

		return categoriaRetorno.isPresent() ? new ResponseEntity<Categoria>(categoriaRetorno.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	// Anotação "@PreAuthorize" é usada para definir a permissao para cada método no controller, usando Oauth2

}
