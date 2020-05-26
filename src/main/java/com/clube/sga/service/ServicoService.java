package com.clube.sga.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clube.sga.datatables.Datatables;
import com.clube.sga.datatables.DatatablesColunas;
import com.clube.sga.domain.Servico;
import com.clube.sga.repository.ServicoRepository;

@Service
public class ServicoService {

	@Autowired
	private ServicoRepository repository;
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = false)
	public void salvar(Servico especialidade) {
		
		repository.save(especialidade);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarServicos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.SERVICOS);
		Page<?> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findAllByServico(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}


	public Object buscarServicosTeste(String tipoServico, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.SERVICOS);
//		TipoServico tipo = TipoServico.pegaServicoPorDescricao(tipoServico); 
		
		System.out.println("Passo no service com "+ tipoServico + " e o search com "+datatables.getSearch() );
		Page<?> page;
		if (datatables.getSearch().isEmpty())
			page = repository.findAllByTipo(tipoServico, datatables.getPageable());
		else
			page = repository.findAllByTipoSearch(tipoServico, datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	
	@Transactional(readOnly = true)
	public Servico buscarPorId(Long id) {
		
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<String> buscarServicoByTermo(String termo) {
		
		return repository.findServicosByTermo(termo);
	}

	@Transactional(readOnly = true)
	public Set<Servico> buscarPorTitulos(String[] titulos) {
		
		return repository.findByTitulos(titulos);
	}
	
	@Transactional(readOnly = true)
	public Servico buscarPorTitulo(String titulo) {
		
	return repository.findByServicoTitulo(titulo);
	
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarServicosPorMedico(Long id, HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.SERVICOS);
		Page<Servico> page = repository.findById(id, datatables.getPageable()); 
		return datatables.getResponse(page);
	}

	
	
}
