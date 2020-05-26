package com.clube.sga.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clube.sga.domain.Servico;
import com.clube.sga.domain.TipoServico;
import com.clube.sga.service.ServicoService;

@Controller
@RequestMapping("servicos")
public class ServicoController {
	
	@Autowired
	private ServicoService service;

	@GetMapping({"", "/"})
	public String abrir(Servico servico) {

		return "servico/servico";
	}
	
	@PostMapping("/salvar")
	public String salvar(Servico servico, RedirectAttributes attr) {
		service.salvar(servico);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/servicos";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> getServicos(HttpServletRequest request) {

		return ResponseEntity.ok(service.buscarServicos(request));
	}
	

	@GetMapping("/datatables/server/teste/{tipoServico}")
	public ResponseEntity<?> getEspecialidadesPorTipo(@PathVariable("tipoServico") String tipoServico, HttpServletRequest request) {
		System.out.println("Passo aqui com "+tipoServico);
		return ResponseEntity.ok(service.buscarServicosTeste(tipoServico, request)); 
	}
	
	
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("servico", service.buscarPorId(id));
		return "servico/servico";
	}
	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/servicos";
	}
	
	@GetMapping("/titulo")
	public ResponseEntity<?> getEspecialidadesPorTermo(@RequestParam("termo") String termo) {
		List<String> servicos = service.buscarServicoByTermo(termo);
		return ResponseEntity.ok(servicos);
	}
	
	@GetMapping("/datatables/server/medico/{id}")
	public ResponseEntity<?> getEspecialidadesPorMedico(@PathVariable("id") Long id, HttpServletRequest request) {
		
		return ResponseEntity.ok(service.buscarServicosPorMedico(id, request)); 
	}
	
	@ModelAttribute("tipoServicos")
	public TipoServico[] getTipoServicos() {
		return TipoServico.values();
	}
	/******************************************************************************************/
	// buscar medicos por especialidade via ajax
	@GetMapping("/servico/titulo/{titulo}")
	public ResponseEntity<?> getServicosPorTitulo(@PathVariable("titulo") String titulo) {
		return ResponseEntity.ok(service.buscarServicoByTermo(titulo));
	}
}
