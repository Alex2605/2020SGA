package com.clube.sga.domain;

public enum TipoDependente {
	ESPOSO(1, "Esposo(a)"),
	FILHO(2, "Filho(a)"),
	PAIMAE(3,"Pai/Mãe"),
	ENTEADO(4,"Enteado"),
	AGREGADO(5,"Agredado"),
	COMPANHEIRO(6,"Companheiro(a)"),
	TUTELADO(7, "Tutelado"),
	OUTROS(8,"Outros");
	
	private int cod;
	private String descricao;
	private TipoDependente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static TipoDependente toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoDependente x: TipoDependente.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: "+ cod);
	 }

}
