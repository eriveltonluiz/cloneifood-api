package com.erivelton.cloneifood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.erivelton.cloneifood.domain.exception.EntidadeEmUsoException;
import com.erivelton.cloneifood.domain.exception.EntidadeNaoEncontradaException;
import com.erivelton.cloneifood.domain.model.Cozinha;
import com.erivelton.cloneifood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha com código %d não foi encontrada";

	public Cozinha findById(Long id) {
		return cozinhaRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id)));
	}

	public void deleteById(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso", id));
		}
	}
}
