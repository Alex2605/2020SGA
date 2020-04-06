package com.clube.sga.projection;

import com.clube.sga.domain.Especialidade;
import com.clube.sga.domain.Medico;
import com.clube.sga.domain.Paciente;

public interface HistoricoPaciente {

	Long getId();
	
	Paciente getPaciente();
	
	String getDataConsulta();
	
	Medico getMedico();
	
	Especialidade getEspecialidade();
}
