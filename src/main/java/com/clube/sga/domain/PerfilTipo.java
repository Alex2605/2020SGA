package com.clube.sga.domain;

public enum PerfilTipo {
	ADMIN(1, "ADMIN"), 
	MEDICO(2, "MEDICO"), 
	PACIENTE(3, "PACIENTE"), 
	ASSOCIADO(4, "ASSOCIADO"),
	USUARIO(5,"USUARIO");
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
