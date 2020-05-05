package com.clube.sga.domain.sga;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;


@SuppressWarnings("serial")
@Entity
@Table(name = "ASSOCIADOS")
public class Associado extends Pessoa {


//	@NotNull(message = "{NotNull.endereco.uf}")
//	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoAssociado tipoAssociado;

	
	@NotNull
	@PastOrPresent(message = "{PastOrPresent.funcionario.dataEntrada}")
	@DateTimeFormat(iso = ISO.DATE, pattern = "")
	@Column(name= "dtinscricao", nullable = false, columnDefinition = "DATE")
	private LocalDate dataInscricao;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "dtsaida", columnDefinition = "DATE")
	private LocalDate dataSaida;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_id_fk")
	private Endereco endereco;

	@JsonIgnore
	@OneToMany(mappedBy = "associado")
	private List<Dependente> dependentes = new ArrayList<Dependente>();

	@JsonIgnore
	@OneToMany(mappedBy = "associado")
	private List<Visitante> visitantes = new ArrayList<Visitante>();

	public Associado() {
		super();
		// TODO Auto-generated constructor stub
	}


	public TipoAssociado getTipoAssociado() {
		return tipoAssociado;
	}

	public void setTipoAssociado(TipoAssociado tipoAssociado) {
		this.tipoAssociado = tipoAssociado;
	}

	public LocalDate getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(LocalDate dataInscricao) {
		this.dataInscricao = dataInscricao;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	public List<Dependente> getDependentes() {
		return dependentes;
	}


	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}


	public List<Visitante> getVisitantes() {
		return visitantes;
	}


	public void setVisitantes(List<Visitante> visitantes) {
		this.visitantes = visitantes;
	}




	
}
