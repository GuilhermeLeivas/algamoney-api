package com.algaworks.algamoneyapi.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.algaworks.algamoneyapi.model.Pessoa;
import com.algaworks.algamoneyapi.model.Pessoa_;
import com.algaworks.algamoneyapi.repository.filter.PessoaFilter;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override // Aqui é onde fazemos a busca realmente pelas pessoas no banco de dados
	public Iterable<Pessoa> filtrarPessoaPorNome(PessoaFilter pessoaFilter) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);

		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Pessoa> query = entityManager.createQuery(criteria);

		return query.getResultList();
	}

	// Aqui fazemos os filtros, verificando se cada campo esta vazio e se nao
	// estiver é feita a pesquisa no banco pelos parametros
	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {

		List<Predicate> predicates = new ArrayList<>();

		if (!StringUtils.isEmpty(pessoaFilter.getNome())) {

			predicates.add(builder.like(builder.lower(root.get(Pessoa_.NOME)), "%" + pessoaFilter.getNome() + "%"));

		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
