package com.erivelton.cloneifood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erivelton.cloneifood.domain.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long>{

}
