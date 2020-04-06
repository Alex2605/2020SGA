package com.clube.sga.domain;

public enum EstadoCivil {

	SOLTEIRO("01", "Solteiro"),
	CASADO("02", "Casado"),
	DIVORCIADO("03","Divorciado"),
	VIUVO("04", "Viuvo");
	
	private String sigla;
	private String descricao;
	
	EstadoCivil(String sigla, String descricao) {
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescricao() {
		return descricao;
	}	
}
