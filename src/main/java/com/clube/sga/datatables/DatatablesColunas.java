package com.clube.sga.datatables;

public class DatatablesColunas {

	public static final String[] SERVICOS = {"id", "titulo"};

	public static final String[] MEDICOS = {"id", "nome", "crm", "dtInscricao", "especialidades"};
	
	public static final String[] AGENDAMENTOS = {"id", "paciente.nome", "dataConsulta", "medico.nome", "especialidade.titulo"};

	public static final String[] USUARIOS = {"id", "email", "ativo", "perfis"};	
	
	public static final String[] ASSOCIADOS = {"id", "nome", "dataNascimento", "dataInscricao", "estadoCivil", "tipoAssociado.tipoAssociado"};
	
	public static final String[] DEPENDENTES = {"id", "nome", "dataNascimento", "tipoDependente.tipoDependente"};
}