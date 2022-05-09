package com.erivelton.cloneifood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erivelton.cloneifood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{

	// Todas é uma descrição, Containing 
	List<Cozinha> findTodasByNomeContaining(String nome);
}
