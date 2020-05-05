package com.clube.sga.domain.sga;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), USUARIO(2, "USUARIO"), ASSOCIADO(3, "ASSOCIADO");
	
	private long cod;
	private String desc;

	private PerfilTipo(long cod, String desc) {
		this.cod = cod;
		this.desc = desc;
	}

	public long getCod() {
		return cod;
	}

	public String getDesc() {
		return desc;
	}
}
