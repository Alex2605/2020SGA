package com.clube.sga.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clube.sga.domain.Dependente;

public interface DependenteRepository extends JpaRepository<Dependente, Long> {
	
	@Query("select d from Dependente d where d.associado.id = :Id")
	Page<Dependente> findByAssociado(Long Id, Pageable pageable);

}
