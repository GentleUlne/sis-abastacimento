package com.sams.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sams.dto.CidadeDTO;
import com.sams.entities.Cidade;
import com.sams.repositories.CidadeRepository;
import com.sams.resources.exceptions.StandardError;
import com.sams.services.exceptions.DataBaseException;
import com.sams.services.exceptions.ResourceNotFoundException;


@Service
public class CidadeService {
	
	@Autowired
	CidadeRepository repository;
	@Transactional(readOnly =true)
	public List<CidadeDTO> findAll(){
		
	List<Cidade> List =	repository.findAll();
		return List.stream().map(Cidade -> new CidadeDTO(Cidade))
				.collect(Collectors.toList());
		
	}
	@Transactional(readOnly =true)
	public CidadeDTO findById(Long id) {
	Optional<Cidade> obj =	repository.findById(id);
	    
	Cidade Cidade  = obj.orElseThrow(()-> new ResourceNotFoundException("A Cidade solicitada não foi encontrada."));
		
		return new CidadeDTO(Cidade);
	}
	
	@Transactional
	public CidadeDTO insert(CidadeDTO dto) {
		Cidade entity = new Cidade();
		entity.setNome(dto.getNome());
		
		
		entity = repository.save(entity);
		return new CidadeDTO(entity); 
	}
	@Transactional
	public CidadeDTO update(long id, CidadeDTO dto) {
		try {
			Cidade entity = repository.getOne(id);
			entity.setNome(dto.getNome());
		
			return new CidadeDTO(entity); 
			
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("O id "
					+ "da Cidade não foi encontrada");
		}
		
		
	}
	public void delete(long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não foi possivel deletar, O id "
					+ "da Cidade não foi encontrada");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Não foi possivel deletar o Cidade, pois a mesma está em uso");
		}
		
		
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Resource not found");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}

	
	
}
