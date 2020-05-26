package com.clube.sga.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clube.sga.domain.Agendamento;
import com.clube.sga.domain.Servico;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{

	@Query("select s "
			+ "from Servico s "
			+ "where LOWER(s.tipoServico) = LOWER(:tipo) "
			+ "and s.id not in ("
				+ "select a.servico.id "
					+ "from Agendamento a "
					+ "where LOWER(a.servico.tipoServico) = LOWER(:tipo) "
					+ "  and ( a.dataInicio  BETWEEN :dataIni AND :dataFim or a.dataFim  BETWEEN :dataIni AND :dataFim )"
			+ ") "
			+ "order by s.id asc")
	List<Servico> findByServicoIdAndDataNotHorarioAgendado(String tipo, LocalDate dataIni, LocalDate dataFim);
/*
	@Query("select a.id as id,"
				+ "a.paciente as paciente,"
				+ "CONCAT(a.dataConsulta, ' ', a.horario.horaMinuto) as dataConsulta,"
				+ "a.medico as medico,"
				+ "a.especialidade as especialidade "
			+ "from Agendamento a "
			+ "where a.paciente.usuario.email like :email")
	Page<HistoricoPaciente> findHistoricoByPacienteEmail(String email, Pageable pageable);

	@Query("select a.id as id,"
			+ "a.paciente as paciente,"
			+ "CONCAT(a.dataConsulta, ' ', a.horario.horaMinuto) as dataConsulta,"
			+ "a.medico as medico,"
			+ "a.especialidade as especialidade "
		+ "from Agendamento a "
		+ "where a.medico.usuario.email like :email")	
	Page<HistoricoPaciente> findHistoricoByMedicoEmail(String email, Pageable pageable);

	@Query("select a from Agendamento a "
			+ "where "
			+ "	(a.id = :id AND a.paciente.usuario.email like :email) "
			+ " OR "
			+ " (a.id = :id AND a.medico.usuario.email like :email)")
	Optional<Agendamento> findByIdAndPacienteOrMedicoEmail(Long id, String email);
*/
}
