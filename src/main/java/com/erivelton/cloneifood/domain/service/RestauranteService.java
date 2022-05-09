package com.erivelton.cloneifood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erivelton.cloneifood.domain.model.Restaurante;
import com.erivelton.cloneifood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	public String save(Restaurante restaurante) {
		restaurante = restauranteRepository.save(restaurante);
		
		StringBuilder sb = new StringBuilder();
		sb.append(restaurante.getId())
			.append(restaurante.getTaxaFrete())
			.append(restaurante.getNome())
			.append(restaurante.getDataCadastro())
			.append(restaurante.getDataAtualizacao())
			.append(restaurante.getCozinha().getNome());
		
		return sb.toString();
	}
}
