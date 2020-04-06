package com.clube.sga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clube.sga.domain.Associado;
import com.clube.sga.repository.AssociadoRepository;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository repository;
	
	@Transactional(readOnly = true)
	public Associado buscarPorUsuarioEmail(String email) {
		
		return repository.findByUsuarioEmail(email).orElse(new Associado());
	}

	@Transactional(readOnly = false)
	public void salvar(Associado Associado) {
		
		repository.save(Associado);		
	}

	@Transactional(readOnly = false)
	public void editar(Associado Associado) {
		Associado p2 = repository.findById(Associado.getId()).get();
		p2.setNome(Associado.getNome());
		p2.setCPF(Associado.getCPF());
		p2.setDataNascimento(Associado.getDataNascimento());
		p2.setEstadoCivil(Associado.getEstadoCivil());
	}
}
