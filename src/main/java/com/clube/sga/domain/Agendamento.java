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
@Table(name = "agendamentos") 
public class Agendamento extends AbstractEntity {
	
	
	@ManyToOne
	@JoinColumn(name="id_servico")
	private Servico servico;
	
	@ManyToOne
	@JoinColumn(name="id_associado")
	private Associado associado;
	
	@Column(name="dtinicio")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataInicio;
	
	@Column(name="dtfim")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dataFim;


	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	
	@Override
	public String toString() {
		return "Agendamento [servico=" + servico.getId() + ", associado=" + associado + ", dataInicio=" + dataInicio
				+ ", dataFim=" + dataFim + "]";
	}	
	
}
