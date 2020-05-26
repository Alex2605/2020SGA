package com.clube.sga.domain;

import java.util.HashMap;
import java.util.Map;

public enum TipoServico {

		CHALE(1, "Chalé"),
		CHURRASQUEIRA(2, "Churrasqueira"),
		SALAO(3,"Salão de Festas");
		
		private int cod;
		private String descricao;
		private TipoServico(int cod, String descricao) {
			this.cod = cod;
			this.descricao = descricao;
		}
		
		private static final Map<String, TipoServico> TipoFUuncaoPorValor = new HashMap<>();
		
		static {
			for (TipoServico tipoServico : TipoServico.values()) {
				TipoFUuncaoPorValor.put(tipoServico.getDescricao(), tipoServico);
			}
		}
		
		public static TipoServico pegaServicoPorDescricao(String descricao) {
			return TipoFUuncaoPorValor.get(descricao);
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
		
		public static TipoServico toEnum(Integer cod) {
			if (cod == null) {
				return null;
			}
			for (TipoServico x: TipoServico.values()) {
				if (cod.equals(x.getCod())) {
					return x;
				}
			}
			throw new IllegalArgumentException("Id inválido: "+ cod);
		 }

}
