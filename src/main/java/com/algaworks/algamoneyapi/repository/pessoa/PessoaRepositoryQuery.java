package com.algaworks.algamoneyapi.repository.pessoa;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.repository.filter.PessoaFilter;
// interface para intermediar nosso repository original com esse, para codigo mais separado
public interface PessoaRepositoryQuery {
	
	// método que fara a busca da Pessoa por nome, ele sera implementado na classe PessoaRepositoryImpl
	public Iterable<Pessoa> filtrarPessoaPorNome(PessoaFilter pessoaFilter);

}
