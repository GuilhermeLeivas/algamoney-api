package com.algaworks.algamoneyapi.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

// Essa classe serve para informar os parametros que iramos receber no filtro de pesquisa dos lançamentos
public class LancamentoFilter {

	private String descricao;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDe;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoAte;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataVencimentoDe() {
		return dataVencimentoDe;
	}

	public void setDataVencimentoDe(LocalDate dataVencimentoDe) {
		this.dataVencimentoDe = dataVencimentoDe;
	}

	public LocalDate getDataVencimentoAte() {
		return dataVencimentoAte;
	}

	public void setDataVencimentoAte(LocalDate dataVencimentoAte) {
		this.dataVencimentoAte = dataVencimentoAte;
	}

}
