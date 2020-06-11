package com.clube.sga.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clube.sga.datatables.Datatables;
import com.clube.sga.datatables.DatatablesColunas;
import com.clube.sga.domain.Agendamento;
import com.clube.sga.domain.Servico;
import com.clube.sga.repository.AgendamentoRepository;


@Service
public class AgendamentoService {
	
	@Autowired
	private AgendamentoRepository repository;

	@Autowired
	Datatables datatables;

	@Transactional(readOnly = true)
	public List<Servico> buscarServicosNaoAgendadosPorIdEData(String tipo, LocalDate dataIni, LocalDate dataFim) {
		
		return repository.findByServicoIdAndDataNotHorarioAgendado(tipo, dataIni, dataFim);
	}

	@Transactional(readOnly = false)
	public void salvar(Agendamento agendamento) {

		repository.save(agendamento);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> buscarAgendamento(Long Id, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.AGENDAMENTOS);
		Page<?> page = repository.findByAssociadoOrAll(Id, datatables.getPageable()); 
		return datatables.getResponse(page);
	}

	
	
/*
	@Transactional(readOnly = true)
	public Map<String, Object> buscarHistoricoPorPacienteEmail(String email, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.AGENDAMENTOS);
		Page<HistoricoPaciente> page = repository.findHistoricoByPacienteEmail(email, datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarHistoricoPorMedicoEmail(String email, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.AGENDAMENTOS);
		Page<HistoricoPaciente> page = repository.findHistoricoByMedicoEmail(email, datatables.getPageable());
		return datatables.getResponse(page);
	}
*/
	@Transactional(readOnly = true)
	public Agendamento buscarPorId(Long id) {
		
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void editar(Agendamento agendamento, String email) {
//		Agendamento ag = buscarPorIdEUsuario(agendamento.getId(), email);
//		ag.setDataConsulta(agendamento.getDataConsulta());
//		ag.setEspecialidade(agendamento.getEspecialidade());
//		ag.setHorario(agendamento.getHorario());
//		ag.setMedico(agendamento.getMedico());
        System.out.println("Consertar esta parte");
	}
/*
	@Transactional(readOnly = true)
	public Agendamento buscarPorIdEUsuario(Long id, String email) {
		
		return repository
				.findByIdAndPacienteOrMedicoEmail(id, email)
				.orElseThrow(() -> new AcessoNegadoException("Acesso negado ao usuário: " + email));
	}
*/
	@Transactional(readOnly = false)
	public void remover(Long id) {
		
		repository.deleteById(id);
	}

}
