package com.clube.sga.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@SuppressWarnings("serial")
@Entity
@Table(name = "VISITANTES")
public class Visitante extends Pessoa {

	
	@DateTimeFormat(iso = ISO.DATE, pattern = "")
	@Column(name= "dtvisita", nullable = false, columnDefinition = "DATE")
	private LocalDate dataVisita;

		
	@ManyToOne
	@JoinColumn(name = "associado_id")
	private Associado associado;

	public Visitante() {
		super();
		// TODO Auto-generated constructor stub
	}


	public LocalDate getDataInscricao() {
		return dataVisita;
	}

	public void setDataInscricao(LocalDate dataVisita) {
		this.dataVisita = dataVisita;
	}

	public Associado getAssociado() {
		return associado;
	}


	public void setAssociado(Associado associado) {
		this.associado = associado;
	}
	
}
