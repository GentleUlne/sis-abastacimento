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

import com.sams.dto.LotacaoDTO;
import com.sams.entities.Lotacao;
import com.sams.repositories.LotacaoRepository;
import com.sams.resources.exceptions.StandardError;
import com.sams.services.exceptions.DataBaseException;
import com.sams.services.exceptions.ResourceNotFoundException;


@Service
public class LotacaoService {
	
	@Autowired
	LotacaoRepository repository;
	@Transactional(readOnly =true)
	public List<LotacaoDTO> findAll(){
		
	List<Lotacao> List =	repository.findAll();
		return List.stream().map(Lotacao -> new LotacaoDTO(Lotacao))
				.collect(Collectors.toList());
		
	}
	@Transactional(readOnly =true)
	public LotacaoDTO findById(Long id) {
	Optional<Lotacao> obj =	repository.findById(id);
	    
	Lotacao Lotacao  = obj.orElseThrow(()-> new ResourceNotFoundException("A Lotacao solicitada não foi encontrada."));
		
		return new LotacaoDTO(Lotacao);
	}
	
	@Transactional
	public LotacaoDTO insert(LotacaoDTO dto) {
		Lotacao entity = new Lotacao();
		entity.setDescricao(dto.getDescricao());
		entity.setEmail(dto.getEmail());
		entity.setEndereco(dto.getEndereco());
		entity.setSite(dto.getSite());
		entity.setTelefone(dto.getTelefone());
		
		entity = repository.save(entity);
		return new LotacaoDTO(entity); 
	}
	@Transactional
	public LotacaoDTO update(long id, LotacaoDTO dto) {
		try {
			Lotacao entity = repository.getOne(id);
			entity.setDescricao(dto.getDescricao());
			entity.setEmail(dto.getEmail());
			entity.setEndereco(dto.getEndereco());
			entity.setSite(dto.getSite());
			entity.setTelefone(dto.getTelefone());
			entity = repository.save(entity);
			return new LotacaoDTO(entity); 
			
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("O id "
					+ "da Lotacao não foi encontrada");
		}
		
		
	}
	public void delete(long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Não foi possivel deletar, O id "
					+ "da Lotacao não foi encontrada");
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Não foi possivel deletar a Lotacao, pois a mesma está em uso");
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
