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

import com.sams.dto.AutoPostoDTO;
import com.sams.entities.AutoPosto;
import com.sams.repositories.AutoPostoRepository;
import com.sams.resources.exceptions.StandardError;
import com.sams.services.exceptions.DataBaseException;
import com.sams.services.exceptions.ResourceNotFoundException;


@Service
public class AutoPostoService {
	
	@Autowired
	AutoPostoRepository repository;
	@Transactional(readOnly =true)
	public List<AutoPostoDTO> findAll(){
		
	List<AutoPosto> List =	repository.findAll();
		return List.stream().map(AutoPosto -> new AutoPostoDTO(AutoPosto))
				.collect(Collectors.toList());
		
	}
	@Transactional(readOnly =true)
	public AutoPostoDTO findById(Long id) {
	Optional<AutoPosto> obj =	repository.findById(id);
	    
	AutoPosto AutoPosto  = obj.orElseThrow(()-> new ResourceNotFoundException("O Auto posto solicitado não foi encontrado."));
		
		return new AutoPostoDTO(AutoPosto);
	}
	
	@Transactional
	public AutoPostoDTO insert(AutoPostoDTO dto) {
		AutoPosto entity = new AutoPosto();
		
		
		entity.setNomeFantasia(dto.getNomeFantasia());
		entity.setTelefone(dto.getTelefone());
		entity.setEmail(dto.getEmail());
		entity.setCNPJ(dto.getCNPJ());
		entity.setEndereco(dto.getEndereco());
		entity = repository.save(entity);
		return new AutoPostoDTO(entity); 
	}
	@Transactional
	public AutoPostoDTO update(long id, AutoPostoDTO dto) {
		try {
			AutoPosto  entity = repository.getOne(id);
			entity.setNomeFantasia(dto.getNomeFantasia());
			entity.setTelefone(dto.getTelefone());
			entity.setEmail(dto.getEmail());
			entity.setCNPJ(dto.getCNPJ());
			entity.setEndereco(dto.getEndereco());
			entity = repository.save(entity);
			return new AutoPostoDTO(entity); 
			
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("O id "
					+ "do auto posto não foi encontrado");
		}
		
		
	}
	public void delete(long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não foi possivel deletar, O id "
					+ "do Auto posto não foi encontrada");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Não foi possivel deletar o Auto Posto, pois a mesma está em uso");
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
