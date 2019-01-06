package com.algaworks.algamoneyapi.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

// esta é nossa classe de Profile,que serve para mudarmos certas configs no projeto quando estivermos em produção
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

	private String origemPermitida = "http://localhost:8000";

	private final Seguranca seguranca = new Seguranca();

	public String getOrigemPermitida() {
		return origemPermitida;
	}

	public void setOrigemPermitida(String origemPermitida) {
		this.origemPermitida = origemPermitida;
	}

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public static class Seguranca {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

	}

}
