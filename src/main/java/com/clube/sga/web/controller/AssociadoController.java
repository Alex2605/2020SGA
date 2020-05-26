package com.clube.sga.web.controller;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clube.sga.domain.Associado;
import com.clube.sga.domain.Dependente;
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
	
	// abrir pagina de dados dos dependentes do Associado	
	@GetMapping("/dependentes")
	public String cadastraDependente(Dependente dependente, ModelMap model, @AuthenticationPrincipal User user) {
		Associado associado = service.buscarPorUsuarioEmail(user.getUsername());
		dependente.setAssociado(associado);
		model.addAttribute("dependente", dependente);
		System.out.println("Associado - "+associado.getNome());
		return "associado/cadastrodependente";
	}

	
	// salvar o form de dados pessoais do Associado com verificacao de senha
	@PostMapping("/salvar")
	public String salvar(Associado associado, ModelMap model, @AuthenticationPrincipal User user) {
		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
		if (UsuarioService.isSenhaCorreta(associado.getUsuario().getSenha(), u.getSenha())) {
			Associado a2 = service.buscarPorUsuarioEmail(user.getUsername());
			if (a2.hasNotId()) {
				System.out.println("Primeira vez. Gravar o associado");
				associado.setUsuario(u);
				associado.setDataInscricao(LocalDate.now());
				associado.setTipoAssociado(TipoAssociado.EFETIVO);
				service.salvar(associado);
				model.addAttribute("sucesso", "Seus dados foram inseridos com sucesso.");
			} else {
				System.out.println("Alterar um associado");
				service.editar(associado);
				model.addAttribute("sucesso", "Seus dados foram alterados com sucesso.");				
			}
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
		return "associado/cadastro";
	}
	
	@PostMapping("/salvarDependente")
	public String salvarDependente(Dependente dependente, ModelMap model, @AuthenticationPrincipal User user) {
		Associado associado = service.buscarPorUsuarioEmail(user.getUsername());
        System.out.println(associado.getNome());
        System.out.println(dependente.getNome());
        associado.getDependentes().addAll(Arrays.asList(dependente));
        System.out.println("Vindo de associados "+associado.getDependentes());
        service.editar(associado);
        System.out.println("Volta de salvar");
/*        
        if (!medico.getEspecialidades().isEmpty()) {
			m2.getEspecialidades().addAll(medico.getEspecialidades());
		}
*/		
        model.addAttribute("sucesso", "Seus dados foram inseridos com sucesso.");
		/**		Usuario u = usuarioService.buscarPorEmail(user.getUsername());
		if (UsuarioService.isSenhaCorreta(associado.getUsuario().getSenha(), u.getSenha())) {
			associado.setUsuario(u);
			associado.setDataInscricao(LocalDate.now());
			associado.setTipoAssociado(TipoAssociado.EFETIVO);
			service.salvar(associado);
			model.addAttribute("sucesso", "Seus dados foram inseridos com sucesso.");
		} else {
			model.addAttribute("falha", "Sua senha não confere, tente novamente.");
		}
*/		
		return "associado/cadastrodependente";
	}
	

	

    // pre edicao de credenciais de Associados
    @GetMapping("/editar/credenciais/associado/{id}")
    public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {

        return new ModelAndView("associado/cadastroporusuario", "associado", service.buscarPorId(id));
    }    

	//Excluir associados
    @GetMapping("/excluir/{id}")
	public String abrir(@PathVariable("id") Long id, RedirectAttributes attr) {
		service.remover(id);
		attr.addFlashAttribute("sucesso", "Operação realizada com sucesso.");
		return "redirect:/u/listar/associados";
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


	// editar o form de dados pessoais do Associado com verificacao de senha
	@PostMapping("/usuario/editar")
	public String usuarioEditar(Associado associado, ModelMap model) {
		service.editar(associado);
		model.addAttribute("sucesso", "Seus dados foram editados com sucesso.");
		return "associado/cadastroporusuario";
		//return "redirect:/u/listar/associados";
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
