package com.clube.sga.domain;

public enum TipoAssociado {
	
	BENEMERITO(10, "Benemérito"),
	EFETIVO(20, "Efetivo"),
	PENSIONISTA(30,"Efetivo Pensionista"),
	FAMILIAR(40,"Familiar"),
	CONVIDADO(50,"Convidado");
	
	private int cod;
	private String descricao;
	private TipoAssociado(int cod, String descricao) {
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
	
	public static TipoAssociado toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoAssociado x: TipoAssociado.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: "+ cod);
	 }


}
