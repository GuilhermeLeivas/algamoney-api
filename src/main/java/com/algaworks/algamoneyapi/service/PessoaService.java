package com.algaworks.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pr;

	// atualiza todos dados da pessoa
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaBuscada = buscarPessoaPeloCodigo(codigo);

		BeanUtils.copyProperties(pessoa, pessoaBuscada, "codigo");
		return pr.save(pessoaBuscada);
	}

	// Atualiza propriedade ativo da pessoa
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaBuscada = buscarPessoaPeloCodigo(codigo);
		pessoaBuscada.setAtivo(ativo);
		pr.save(pessoaBuscada);
	}

	// busca pelo id
	private Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaBuscada = pr.findById(codigo).get();
		if (pessoaBuscada == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaBuscada;
	}
}
