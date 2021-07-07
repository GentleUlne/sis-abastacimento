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

import com.sams.dto.TipoDTO;
import com.sams.entities.Tipo;
import com.sams.repositories.TipoRepository;
import com.sams.resources.exceptions.StandardError;
import com.sams.services.exceptions.DataBaseException;
import com.sams.services.exceptions.ResourceNotFoundException;


@Service
public class TipoService {
	
	@Autowired
	TipoRepository repository;
	@Transactional(readOnly =true)
	public List<TipoDTO> findAll(){
		
	List<Tipo> List =	repository.findAll();
		return List.stream().map(Tipo -> new TipoDTO(Tipo))
				.collect(Collectors.toList());
		
	}
	@Transactional(readOnly =true)
	public TipoDTO findById(Long id) {
	Optional<Tipo> obj =	repository.findById(id);
	    
	Tipo Tipo  = obj.orElseThrow(()-> new ResourceNotFoundException("O Tipo solicitado não foi encontrada."));
		
		return new TipoDTO(Tipo);
	}
	
	@Transactional
	public TipoDTO insert(TipoDTO dto) {
		Tipo entity = new Tipo();
		entity.setDescricao(dto.getDescricao());
		
		
		entity = repository.save(entity);
		return new TipoDTO(entity); 
	}
	@Transactional
	public TipoDTO update(long id, TipoDTO dto) {
		try {
			Tipo entity = repository.getOne(id);
			entity.setDescricao(dto.getDescricao());
		
			return new TipoDTO(entity); 
			
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("O id "
					+ "do Tipo não foi encontrada");
		}
		
		
	}
	public void delete(long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não foi possivel deletar, O id "
					+ "do Tipo não foi encontrada");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Não foi possivel deletar o Tipo, pois o mesmo está em uso");
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
