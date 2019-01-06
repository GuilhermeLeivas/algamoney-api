package com.algaworks.algamoneyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.model.Lancamento;
import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import com.algaworks.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pr;
	
	@Autowired
	private LancamentoRepository lr;
	
	// Método pega a pessoa de um lancamento, verifica se ele é null e se a propriedade "ativo" está como inativo, se uma ou outra for true
	// é lançada a exception criada "PessoaInexistenteOuInativaException".
	public Lancamento salvar(Lancamento lancamento) {
		
		Pessoa pessoa = pr.findById(lancamento.getPessoa().getCodigo()).get();
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lr.save(lancamento);
	}

}
