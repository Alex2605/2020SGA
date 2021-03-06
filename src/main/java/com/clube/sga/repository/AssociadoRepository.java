package com.clube.sga.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clube.sga.domain.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

	@Query("select p from Associado p where p.usuario.email like :email")
	Optional<Associado> findByUsuarioEmail(String email);
	
	@Query("select p from Associado p where p.nome like %:search%") 
	Page<Associado> findBySearchPageable(String search, Pageable pageable);
}
