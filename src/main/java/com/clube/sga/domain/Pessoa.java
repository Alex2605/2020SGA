package com.clube.sga.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
//import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class Pessoa extends AbstractEntity {


	@NotBlank
	@Size(max = 255, min = 3)
	@Column(nullable = false, unique = true)
	private String nome;

//	@Size(min = 12, max = 12, message = "{Size.pessoa.cpf}")
	@Column(nullable = true, unique = true)
	private String CPF;
	
	@NotNull
 	@PastOrPresent(message = "{PastOrPresent.pessoa.dataNascimento}")
	@DateTimeFormat(iso = ISO.DATE, pattern = "")
	@Column(name= "dtnascimento", nullable = false, columnDefinition = "DATE")
	private LocalDate dataNascimento;
	

//	@NotNull(message = "{NotNull.endereco.uf}")
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;	
	

	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", CPF=" + CPF + "]";
	}
}
