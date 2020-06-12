package com.clube.sga.web.controller;

import java.time.LocalDate;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.clube.sga.domain.Associado;
import com.clube.sga.domain.Dependente;
import com.clube.sga.domain.TipoDependente;
import com.clube.sga.exception.AcessoNegadoException;
import com.clube.sga.service.AssociadoService;
import com.clube.sga.service.DependenteService;

@Controller
@RequestMapping("dependentes")
public class DependenteController {
	
	@Autowired
	private DependenteService service;
	
	@Autowired
	private AssociadoService associadoService;

	@GetMapping({"", "/"})
	public String abrir(Dependente dependente, @AuthenticationPrincipal User user) {

		Associado associado = associadoService.buscarPorUsuarioEmail(user.getUsername());
		if (associado.hasNotId()) {
			throw new AcessoNegadoException("Somente Associados cadastrados podem reservar serviços. "
				+ "Faça o seu cadastro na opção Cadastrar dados");
		}

		
		return "dependente/dependente";
	}

	@GetMapping("/datatables/server")
	public ResponseEntity<?> getDependentes(HttpServletRequest request, @AuthenticationPrincipal User user) {
		Associado associado = associadoService.buscarPorUsuarioEmail(user.getUsername());
		return ResponseEntity.ok(service.buscarDependentePorAssociado(associado.getId(), request));
	}

	@PostMapping("/salvar")
	public String salvar(Dependente dependente, RedirectAttributes attr, @AuthenticationPrincipal User user) {
			dependente.setDataInscricao(LocalDate.now());
		Associado associado = associadoService.buscarPorUsuarioEmail(user.getUsername());
		dependente.setAssociado(associado);

        associado.getDependentes().addAll(Arrays.asList(dependente));
		service.salvar(dependente);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/dependentes";
	}

	@PostMapping("/editar")
	public String editar(Dependente dependente, RedirectAttributes attr, @AuthenticationPrincipal User user) {	
		Dependente d2 = service.buscarPorId(dependente.getId());
		d2.setNome(dependente.getNome());
		d2.setDataNascimento(dependente.getDataNascimento());
		d2.setTipoDependente(dependente.getTipoDependente());
		service.salvar(d2);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
		return "redirect:/dependentes";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("dependente", service.buscarPorId(id));
		return"dependente/dependente";
	}	
	@GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/dependentes";
	}	
	@ModelAttribute("tipoDependentes")
	public TipoDependente[] getTipoDependente() {
		return TipoDependente.values();
	}

}
