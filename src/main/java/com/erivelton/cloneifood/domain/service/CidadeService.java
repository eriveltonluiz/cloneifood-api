package com.erivelton.cloneifood.domain.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.erivelton.cloneifood.domain.exception.EntidadeEmUsoException;
import com.erivelton.cloneifood.domain.exception.EntidadeNaoEncontradaException;
import com.erivelton.cloneifood.domain.exception.NegocioException;
import com.erivelton.cloneifood.domain.model.Cidade;
import com.erivelton.cloneifood.domain.model.Estado;
import com.erivelton.cloneifood.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com código %d";

	@Autowired
	private CidadeRepository cidadeRepository;

//	@Autowired
//	private EstadoService estadoService;
	
	@PersistenceContext
	private EntityManager em;

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.idEstado();

//		Estado estado = estadoService.buscarOuFalhar(estadoId);
		Estado estado = em.find(Estado.class, estadoId);
		if(estado == null)
			throw new NegocioException(String.format("Estado com código %d", estadoId));
			
		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}

	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
	}
}
