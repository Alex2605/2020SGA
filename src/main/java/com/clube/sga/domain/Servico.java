package com.clube.sga.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "servicos", indexes = {@Index(name = "idx_especialidade_titulo", columnList = "titulo")})
public class Servico extends AbstractEntity {
	

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoServico tipoServico;
	
	@Column(name = "titulo", unique = true, nullable = false)
	private String titulo;
	
	@Column(name = "descricao", columnDefinition = "TEXT")
	private String descricao;
	
	@Column(name = "ativo", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean ativo;

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "Servico [tipoServico=" + tipoServico + ", titulo=" + titulo + ", descricao=" + descricao + ", ativo="
				+ ativo + "]";
	}
	
	
	
}
