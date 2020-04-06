package com.clube.sga.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.clube.sga.domain.Associado;

public interface AssociadoRepository extends JpaRepository<Associado, Long> {

	@Query("select p from Associado p where p.usuario.email like :email")
	Optional<Associado> findByUsuarioEmail(String email);
}
