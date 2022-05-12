package com.erivelton.cloneifood.api.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erivelton.cloneifood.domain.model.Restaurante;
import com.erivelton.cloneifood.domain.repository.RestauranteRepository;
import com.erivelton.cloneifood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@PostMapping
	public ResponseEntity<String> save(@RequestBody @Valid Restaurante restaurante) {
		String retorno = restauranteService.save(restaurante);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
	}
	
	@GetMapping
	public List<Restaurante> listar(){
		List<Restaurante> restaurantes = restauranteRepository.findAll();
		restaurantes.get(0).getCozinha().getNome();
		return restaurantes;
	}
	
	@GetMapping("/por-restaurante")
	public Optional<Restaurante> primeiroRestaurante(String nome){
		return restauranteRepository.findFirstByNomeContaining(nome);
	}
	
	@GetMapping("/top2-por-nome")
	public List<Restaurante> listarTop2(String nome){
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}

	@GetMapping("/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome){
//		var comFreteGratis = new RestauranteComFreteGratisSpec() ;
//		var comNomeSemelhante = new RestauranteComNomeSemelhanteSpec(nome);
		
//		return restauranteRepository.findAll(comFreteGratis.and(comNomeSemelhante)); 1º caso com um spec para cada funcionalidade
		
//		return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis()
//				.and(RestauranteSpecs.comNomeSemelhante(nome)));     2º caso com uma fabrica de specs para todas as funcionalidades
		
		return restauranteRepository.findComFreteGratis(nome);    //3 º caso tirando a responsabilidade do método de chamar métodos estáticos que não a pertencem
	}
}
