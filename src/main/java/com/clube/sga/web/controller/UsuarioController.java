package com.clube.sga.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clube.sga.domain.Perfil;
import com.clube.sga.domain.PerfilTipo;
import com.clube.sga.domain.Usuario;
import com.clube.sga.service.AssociadoService;
import com.clube.sga.service.UsuarioService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
@RequestMapping("u")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;
	
//	@Autowired
//	private MedicoService medicoService;
	
	@Autowired
	private AssociadoService associadoService;
	
	@Autowired
	private DataSource dataSource;

	
	// abrir cadastro de usuarios (medico/admin/paciente)
    @GetMapping("/novo/cadastro/usuario")
    public String cadastroPorAdminParaAdminMedicoPaciente(Usuario usuario) {

        return "usuario/cadastro";
    }

    // abrir lista de associados - acho que deve ficar no controler do usuário
    @GetMapping("/listar/associados")
    public String listarAssociados() {
        return "associado/lista";
    }  
    
    
    // abrir lista de usuarios
    @GetMapping("/lista")
    public String listarUsuarios() {

        return "usuario/lista";
    }  

    // listar usuarios na datatables
    @GetMapping("/datatables/server/usuarios")
    public ResponseEntity<?> listarUsuariosDatatables(HttpServletRequest request) {
        return ResponseEntity.ok(service.buscarTodos(request));
    } 


	// Manutenção Alex novas funcionalidades
    // listar associados na datatables
    @GetMapping("/datatables/server/associados")
    public ResponseEntity<?> listarAssociadosDatatables(HttpServletRequest request) {
    	System.out.println("Entro aqui");
        return ResponseEntity.ok(associadoService.buscarTodos(request));
    }	
    
    
    // salvar cadastro de usuarios por administrador
    @PostMapping("/cadastro/salvar")
    public String salvarUsuarios(Usuario usuario, RedirectAttributes attr) {
    	List<Perfil> perfis = usuario.getPerfis();
    	if (perfis.size() > 2 || 
    			perfis.containsAll(Arrays.asList(new Perfil(1L), new Perfil(3L))) ||
    			perfis.containsAll(Arrays.asList(new Perfil(2L), new Perfil(3L)))) {
    		attr.addFlashAttribute("falha", "Paciente não pode ser Admin e/ou Médico.");
    		attr.addFlashAttribute("usuario", usuario);
    	} else {
    		try {
    			service.salvarUsuario(usuario); 
    			attr.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
    		} catch (DataIntegrityViolationException ex) {
    			attr.addFlashAttribute("falha", "Cadastro não realizado, email já existente.");
			}
    	}
    	return "redirect:/u/novo/cadastro/usuario";
    }
    
    // pre edicao de credenciais de usuarios
    @GetMapping("/editar/credenciais/usuario/{id}")
    public ModelAndView preEditarCredenciais(@PathVariable("id") Long id) {

        return new ModelAndView("usuario/cadastro", "usuario", service.buscarPorId(id));
    }    
    
    // pre edicao de cadastro de usuarios
    @GetMapping("/editar/dados/usuario/{id}/perfis/{perfis}")
    public ModelAndView preEditarCadastroDadosPessoais(@PathVariable("id") Long usuarioId, 
    												   @PathVariable("perfis") Long[] perfisId) {
    	Usuario us = service.buscarPorIdEPerfis(usuarioId, perfisId);
    	
    	if (us.getPerfis().contains(new Perfil(PerfilTipo.ADMIN.getCod())) &&
    		!us.getPerfis().contains(new Perfil(PerfilTipo.USUARIO.getCod())) ) {
    		
    		return new ModelAndView("usuario/cadastro", "usuario", us);
/*    		
    	} else if (us.getPerfis().contains(new Perfil(PerfilTipo.MEDICO.getCod()))) {
    		
    		Medico medico = medicoService.buscarPorUsuarioId(usuarioId);
    		return medico.hasNotId()
    				? new ModelAndView("medico/cadastro", "medico", new Medico(new Usuario(usuarioId)))
    				: new ModelAndView("medico/cadastro", "medico", medico);
*/
    	} else if (us.getPerfis().contains(new Perfil(PerfilTipo.ASSOCIADO.getCod()))) {
    		ModelAndView model = new ModelAndView("error");
    		model.addObject("status", 403);
    		model.addObject("error", "Área Restrita");
    		model.addObject("message", "Os dados de pacientes são restritos a ele.");
    		return model;
    	}
    	
        return new ModelAndView("redirect:/u/lista");
    }  
    
    @GetMapping("/editar/senha")
    public String abrirEditarSenha() {
    	
    	return "usuario/editar-senha";
    }
    
    @PostMapping("/confirmar/senha")
    public String editarSenha(@RequestParam("senha1") String s1, @RequestParam("senha2") String s2, 
    						  @RequestParam("senha3") String s3, @AuthenticationPrincipal User user,
    						  RedirectAttributes attr) {
    	
    	if (!s1.equals(s2)) {
    		attr.addFlashAttribute("falha", "Senhas não conferem, tente novamente");
    		return "redirect:/u/editar/senha";
    	}
    	
    	Usuario u = service.buscarPorEmail(user.getUsername());
    	if(!UsuarioService.isSenhaCorreta(s3, u.getSenha())) {
    		attr.addFlashAttribute("falha", "Senha atual não confere, tente novamente");
    		return "redirect:/u/editar/senha";
    	}
    		
    	service.alterarSenha(u, s1);
    	attr.addFlashAttribute("sucesso", "Senha alterada com sucesso.");
    	return "redirect:/u/editar/senha";
    }
    
    // abrir página de novo cadastro de paciente
    @GetMapping("novo/cadastro")
    public String novoCadastro(Usuario usuario) {
    	return "cadastrar-se";
    }
    
    // página de resposta do cadastro de paciente
    @GetMapping("cadastro/realizado")
    public String cadastroRealizado() {
    	return "fragments/mensagem";
    }
    
    //recebe o from da página cadastrar-se
    @PostMapping("/cadastro/paciente/salvar")
    public String salvarCadastroPaciente(Usuario usuario, BindingResult result) throws MessagingException {
    	try {
        	service.salvarCadastroPaciente(usuario);
		} catch (DataIntegrityViolationException ex) {
			result.reject("email", "Ops... Este email já existe na base de dados.");
			return "cadastrar-se";
		}
    	return "redirect:/u/cadastro/realizado";
    }
    
    // recebe a requisição de confirmação de cadastro
    @GetMapping("/confirmacao/cadastro")
    public String respostaConfirmacaoCadastroPaciente(@RequestParam("codigo") String codigo,
    												  RedirectAttributes attr) {
    	service.ativarCadastroPaciente(codigo);
    	attr.addFlashAttribute("alerta","sucesso");
    	attr.addFlashAttribute("titulo","Cadastro Ativado");
    	attr.addFlashAttribute("texto","Parabéns, seu cadastro está ativo");
    	attr.addFlashAttribute("subtexto","Siga com seu login/senha");
    	return "redirect:/login";
    }
    
    // abre a página de pedido de redefinição de senha
    @GetMapping("/p/redefinir/senha")
    public String pedidoRedefinirSenha() {
    	
    	return "usuario/pedido-recuperar-senha";
    }
    
    //form de pedido de recuperar senha
    @GetMapping("/p/recuperar/senha")
    public String redefinirSenha(String email, ModelMap model) throws MessagingException {
    	service.pedidoRedefinicaoDeSenha(email);
    	model.addAttribute("sucesso", "Em instantes você receberá um e-mail para "
    				                   + "prosseguir com a redefinição de sua senha");
    	model.addAttribute("usuario", new Usuario(email));
    	return "usuario/recuperar-senha";
    }
    
    //salvar a nova senha via recuperacao de senha
    @PostMapping("/p/nova/senha")
    public String confirmacaoRedefinicaoDeSenha(Usuario usuario, ModelMap model) {
    	Usuario u = service.buscarPorEmail(usuario.getEmail());
    	if (!usuario.getCodigoVerificador().equals(u.getCodigoVerificador())) {
    		model.addAttribute("falha", "Código verificado não confere");
    		return "usuario/recuperar-senha";
		}
    	u.setCodigoVerificador(null);
    	service.alterarSenha(u, usuario.getSenha());
    	model.addAttribute("alerta", "Sucesso");
    	model.addAttribute("titulo", "Senha redefinida!");
    	model.addAttribute("texto", "Você já pode logar no sistema");
    	return "login";
    	
    }
    
    

    
@GetMapping("/listar/associados/imprimir")
public void imprimir(Map<String, Object> parametros, HttpServletResponse response) throws JRException, SQLException, IOException {
	
		
	parametros = parametros == null ? parametros = new HashMap<>() : parametros;
	
	// Pega o arquivo .jasper localizado em resources
	//InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/Associados.jasper");
	InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/Associados.jasper");
	
	// Cria o objeto JaperReport com o Stream do arquivo jasper
	JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
	// Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no caso uma conexão ao banco de dados
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());

	// Configura a respota para o tipo PDF
	response.setContentType("application/pdf");
	// Define que o arquivo pode ser visualizado no navegador e também nome final do arquivo
	// para fazer download do relatório troque 'inline' por 'attachment'
	response.setHeader("Content-Disposition", "inline; filename=Associados.pdf");

	// Faz a exportação do relatório para o HttpServletResponse
	final OutputStream outStream = response.getOutputStream();
	JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
}
    
    
    
    
    
    
    
    
    
}
