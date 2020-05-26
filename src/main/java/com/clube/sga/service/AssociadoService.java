package com.clube.sga.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clube.sga.datatables.Datatables;
import com.clube.sga.datatables.DatatablesColunas;
import com.clube.sga.domain.Associado;
import com.clube.sga.repository.AssociadoRepository;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository repository;
	@Autowired
	private Datatables datatables;
	
	
	@Transactional(readOnly = true)
	public Associado buscarPorUsuarioEmail(String email) {
		
		return repository.findByUsuarioEmail(email).orElse(new Associado());
	}

	@Transactional(readOnly = false)
	public void salvar(Associado associado) {
		repository.save(associado);		
	}

	@Transactional(readOnly = false)
	public void editar(Associado associado) {
		
		Associado p2 = repository.findById(associado.getId()).get();
		p2.setNome(associado.getNome());
		p2.setCPF(associado.getCPF());
		p2.setDataNascimento(associado.getDataNascimento());
		p2.setEstadoCivil(associado.getEstadoCivil());
		p2.setTipoAssociado(associado.getTipoAssociado());
		p2.setEndereco(associado.getEndereco());

		if (!associado.getDependentes().isEmpty()) {
			System.out.println("Insere o dependente "+associado.getDependentes());
			p2.getDependentes().addAll(associado.getDependentes());
		}
		salvar(p2);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.ASSOCIADOS);
		Page<Associado> page = datatables.getSearch().isEmpty()
				? repository.findAll(datatables.getPageable())
				: repository.findBySearchPageable(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = true)
	public Associado buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public void remover(Long id) {
		repository.deleteById(id);
	}
}
