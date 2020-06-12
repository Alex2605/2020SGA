package com.clube.sga.web.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clube.sga.domain.Agendamento;
import com.clube.sga.domain.Associado;
import com.clube.sga.domain.Servico;
import com.clube.sga.domain.TipoServico;
import com.clube.sga.domain.Usuario;
import com.clube.sga.exception.AcessoNegadoException;
import com.clube.sga.service.AgendamentoService;
import com.clube.sga.service.AssociadoService;
import com.clube.sga.service.ServicoService;


@Controller
@RequestMapping("agendamentos")
public class AgendamentoController {
	
	@Autowired
	private AgendamentoService service;
	@Autowired
	private AssociadoService associadoService;
	@Autowired
	private ServicoService servicoService;	

	// abre a pagina de agendamento de consultas 
	@GetMapping({"/agendar"})
	public String agendarConsulta(Agendamento agendamento, @AuthenticationPrincipal User user) {

		Associado associado = associadoService.buscarPorUsuarioEmail(user.getUsername());
		if (associado.hasNotId()) {
			throw new AcessoNegadoException("Somente Associados cadastrados podem reservar serviços. "
					+ "Faça o seu cadastro na opção Cadastrar dados");

		}
	
		return "agendamento/cadastro";		
	}

	// abre a pagina de agendamento de consultas 
	@GetMapping({"/consultar"})
	public String ConsultaAgendamento(Agendamento agendamento) {
		return "agendamento/historico-associado";		
	}
	
	
	// busca os servicos livres, ou seja, sem agendamento
	@GetMapping("/servico/{tipo}/dataIni/{dataIni}/dataFim/{dataFim}")
	public ResponseEntity<?> getServicos(@PathVariable("tipo") String tipo,
										 @PathVariable("dataIni") @DateTimeFormat(iso = ISO.DATE) LocalDate dataIni,
										 @PathVariable("dataFim") @DateTimeFormat(iso = ISO.DATE) LocalDate dataFim){
		System.out.println("Passo aqui com os parâmetros "
				+ tipo +", "+ dataIni+", "+dataFim);
		return ResponseEntity.ok(service.buscarServicosNaoAgendadosPorIdEData(tipo, dataIni, dataFim));
	}
	
	// salvar um consulta agendada
	@PostMapping({"/salvar"})
	public String salvar(Agendamento agendamento, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		Associado associado = associadoService.buscarPorUsuarioEmail(user.getUsername());
		Servico servico = servicoService.buscarPorId(agendamento.getServico().getId());
		agendamento.setServico(servico);
		agendamento.setAssociado(associado);
		service.salvar(agendamento);
		attr.addFlashAttribute("sucesso", "Sua reserva foi efetuada com sucesso. ");
		return "redirect:/agendamentos/agendar";		
	}
	
	// abrir pagina de historico de agendamento do associado
	@GetMapping({"/historico/associado", "/historico/consultas"})
	public String historico() {

		return "agendamento/historico-associado";
	}
/*	
	// localizar o historico de agendamentos por usuario logado
	@GetMapping("/datatables/server/historico")
	public ResponseEntity<?> historicoAgendamentosPorAssociado(HttpServletRequest request, @AuthenticationPrincipal User user) {
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.PACIENTE.getDesc()))) {
			
			return ResponseEntity.ok(service.buscarHistoricoPorAssociadoEmail(user.getUsername(), request));
		}
		
		if (user.getAuthorities().contains(new SimpleGrantedAuthority(PerfilTipo.MEDICO.getDesc()))) {
			
			return ResponseEntity.ok(service.buscarHistoricoPorMedicoEmail(user.getUsername(), request));
		}		
		
		return ResponseEntity.notFound().build();
	}
*/	
	// localizar agendamento pelo id e envia-lo para a pagina de cadastro
	@GetMapping("/edicao/{id}") 
	public String preEditarConsultaAssociado(@PathVariable("id") Long id, 
										    ModelMap model, @AuthenticationPrincipal User user) {
		
//		Agendamento agendamento = service.buscarPorIdEUsuario(id, user.getUsername());
		
//		model.addAttribute("agendamento", agendamento);
		return "agendamento/cadastro";
	}
	
	@PostMapping("/editar")
	public String editarConsulta(Agendamento agendamento, RedirectAttributes attr, @AuthenticationPrincipal User user) {
		String titulo = agendamento.getServico().getTitulo();
		Servico servico = servicoService.buscarPorTitulo(titulo);
		agendamento.setServico(servico);
		
		service.editar(agendamento, user.getUsername());
		attr.addFlashAttribute("sucesso", "Sua Reserva foi alterada com sucesso.");
		return "redirect:/agendamentos/agendar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluirConsulta(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Consulta excluída com sucesso.");
		return "redirect:/agendamentos/historico/associado";
	}

	@GetMapping("/datatables/server")
	public ResponseEntity<?> getAssociados(HttpServletRequest request, @AuthenticationPrincipal User user) {
		Associado associado = associadoService.buscarPorUsuarioEmail(user.getUsername());
		return ResponseEntity.ok(service.buscarAgendamento(associado.getId(), request));
	}

	
	@ModelAttribute("tipoServicos")
	public TipoServico[] getTipoServicos() {
		return TipoServico.values();
	}
	
}
