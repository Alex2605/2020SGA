package com.clube.sga.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clube.sga.datatables.Datatables;
import com.clube.sga.datatables.DatatablesColunas;
import com.clube.sga.domain.Dependente;
import com.clube.sga.repository.DependenteRepository;

@Service
public class DependenteService {
	
	@Autowired
	DependenteRepository repository;
	
	@Autowired
	Datatables datatables;
	
	@Transactional(readOnly = false)
	public void salvar(Dependente dependente) {
		
		repository.save(dependente);
	}	
	
	@Transactional(readOnly = true)
	public Dependente buscarPorId(Long id) {
		
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarDependentes(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.DEPENDENTES);
		Page<?> page = repository.findAll(datatables.getPageable()); 
		return datatables.getResponse(page);
	}
	
}
