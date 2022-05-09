package com.erivelton.cloneifood.api.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.erivelton.cloneifood.domain.model.Cozinha;
import com.erivelton.cloneifood.domain.repository.CozinhaRepository;
import com.erivelton.cloneifood.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@GetMapping
	public List<Cozinha> listar(){
		return cozinhaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> findById(@PathVariable Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
		
		return ResponseEntity.status(HttpStatus.OK)
				.headers(headers)
				.body(cozinhaService.findById(id));
	}
	
	@PostMapping
	@Transactional
	public void save(@RequestBody Cozinha cozinha) {
		cozinhaRepository.save(cozinha);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<Cozinha> update(@RequestBody Cozinha cozinha, @PathVariable Long id){
		Cozinha cozinhaAtual = cozinhaRepository.findById(id).get();
		
		BeanUtils.copyProperties(cozinha, cozinhaAtual);
//		Cozinha retCozinha = em.merge(cozinha);
		
		return ResponseEntity.ok(cozinhaAtual);
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		cozinhaService.deleteById(id);
//		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}
