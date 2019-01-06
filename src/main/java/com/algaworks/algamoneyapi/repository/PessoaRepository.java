package com.algaworks.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algamoneyapi.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{


}
