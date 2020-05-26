package com.clube.sga.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clube.sga.domain.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long>{

	@Query("select s from Servico s where s.titulo = :titulo")
	Servico findByServicoTitulo(String titulo);
	
	@Query("select e from Servico e where e.tipoServico like :search%")
	Page<Servico> findAllByServico(String search, Pageable pageable);

	@Query("select e.titulo from Servico e where e.titulo like :termo%")
	List<String> findServicosByTermo(String termo);

	@Query("select e from Servico e where e.titulo IN :titulos")
	Set<Servico> findByTitulos(String[] titulos);

	@Query("select e from Servico e "
			+ "where id = :id")
	Page<Servico> findById(Long id, Pageable pageable);
/*
	@Query("select e from Servico e where LOWER(e.tipoServico) = LOWER('CHALE')")
	Page<Servico> findAllByTipo(@Param("tipo") TipoServico tipo, Pageable pageable);
*/	
	@Query("select e from Servico e where LOWER(e.tipoServico) = LOWER(:tipo)")
	Page<Servico> findAllByTipo(@Param("tipo") String tipo, Pageable pageable);	

	@Query("select e from Servico e "
		  + "where LOWER(e.tipoServico) = LOWER(:tipo) "
		  + "  and e.titulo like %:search%")
	Page<Servico> findAllByTipoSearch(@Param("tipo") String tipo, String search, Pageable pageable);

	
}
