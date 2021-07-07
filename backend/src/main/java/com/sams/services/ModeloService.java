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

import com.sams.dto.ModeloDTO;
import com.sams.entities.Modelo;
import com.sams.repositories.ModeloRepository;
import com.sams.resources.exceptions.StandardError;
import com.sams.services.exceptions.DataBaseException;
import com.sams.services.exceptions.ResourceNotFoundException;


@Service
public class ModeloService {
	
	@Autowired
	ModeloRepository repository;
	@Transactional(readOnly =true)
	public List<ModeloDTO> findAll(){
		
	List<Modelo> List =	repository.findAll();
		return List.stream().map(Modelo -> new ModeloDTO(Modelo))
				.collect(Collectors.toList());
		
	}
	@Transactional(readOnly =true)
	public ModeloDTO findById(Long id) {
	Optional<Modelo> obj =	repository.findById(id);
	    
	Modelo Modelo  = obj.orElseThrow(()-> new ResourceNotFoundException("A Modelo solicitada não foi encontrada."));
		
		return new ModeloDTO(Modelo);
	}
	
	@Transactional
	public ModeloDTO insert(ModeloDTO dto) {
		Modelo entity = new Modelo();
		entity.setDescricao(dto.getDescricao());
		
		
		entity = repository.save(entity);
		return new ModeloDTO(entity); 
	}
	@Transactional
	public ModeloDTO update(long id, ModeloDTO dto) {
		try {
			Modelo entity = repository.getOne(id);
			entity.setDescricao(dto.getDescricao());
		
			return new ModeloDTO(entity); 
			
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("O id "
					+ "do Modelo não foi encontrada");
		}
		
		
	}
	public void delete(long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não foi possivel deletar, O id "
					+ "do Modelo não foi encontrada");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Não foi possivel deletar o Modelo, pois a mesma está em uso");
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
