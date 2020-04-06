package com.clube.sga.web.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.clube.sga.domain.Associado;
import com.clube.sga.domain.EstadoCivil;
import com.clube.sga.domain.TipoAssociado;
import com.clube.sga.domain.UF;
import com.clube.sga.domain.Usuario;
import com.clube.sga.service.AssociadoService;
import com.clube.sga.service.UsuarioService;

@Controller
@RequestMapping("associados")
public class AssociadoController {
	
	@Autowired
	private AssociadoService service;
	@Autowired
	private UsuarioService usuarioService;
	
	// abrir pagina de dados pessoais do Associado
	@GetMapping("/dados")
	public String cadastrar(Associado associado, ModelMap model, @AuthenticationPrincipal User user) {
		associado = service.buscarPorUsuarioEmail(user.getUsername());
		if (associado.hasNotId()) {
			associado.setUsuario(new Usuario(user.getUsername()));
		}
		model.addAttribute("associado", associado);
		return "associado/cadastro";
	}
	
	// salvar o form de dados pessoais do Associado com verificacao de senha
	@PostMapping("/salvar")
	public String salvar(Associado associado, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
		if (UsuarioService.isSenhaCorreta(associado.getUsuario().getSenha(), u.getSenha())) {
			associado.setUsuario(u);
			associado.setDataInscricao(LocalDate.now());
			associado.setTipoAssociado(TipoAssociado.EFETIVO);
			service.salvar(associado);
			model.addAttribute("sucesso", "Seus dados foram inseridos com sucesso.");
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
		return "associado/cadastro";
	}	
	
	// editar o form de dados pessoais do Associado com verificacao de senha
	@PostMapping("/editar")
	public String editar(Associado associado, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
		if (UsuarioService.isSenhaCorreta(associado.getUsuario().getSenha(), u.getSenha())) {
			service.editar(associado);
			model.addAttribute("sucesso", "Seus dados foram editados com sucesso.");
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
		return "associado/cadastro";
	}	


	@ModelAttribute("ufs")
	public UF[] getUFs() {
		return UF.values();
	}
	
	@ModelAttribute("tipoAssociados")
	public TipoAssociado[] getTipoAssociado() {
		return TipoAssociado.values();
	}

	@ModelAttribute("estadoCivis")
	public EstadoCivil[] getEstadoCivis() {
		return EstadoCivil.values();
	}

	
}
