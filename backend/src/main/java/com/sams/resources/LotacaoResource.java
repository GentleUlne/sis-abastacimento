package com.sams.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sams.dto.LotacaoDTO;
import com.sams.services.LotacaoService;


@RestController
@RequestMapping(value = "/Lotacoes")
public class LotacaoResource {
	
	@Autowired
	private LotacaoService service ;
	
    @GetMapping
	public ResponseEntity<List<LotacaoDTO>> findAll(){
	List<LotacaoDTO> lista = service.findAll();
	
	return ResponseEntity.ok().body(lista);

    	
	
	}
    
    @GetMapping(value = "/{id}" )
    public ResponseEntity<LotacaoDTO> findById(@PathVariable Long id){
		
    	LotacaoDTO dto = service.findById(id);
    	return ResponseEntity.ok().body(dto);
    	}
    
    @PostMapping
    public ResponseEntity<LotacaoDTO> insert(@RequestBody LotacaoDTO dto){
    	dto = service.insert(dto);
    	// Quando retornar 201 objeto criado, por padrão retorna também o endereço do recurso
    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
    	return ResponseEntity.created(uri).body(dto);

    	}
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<LotacaoDTO> update(@PathVariable long id, @RequestBody LotacaoDTO dto){
    	 dto = service.update(id,dto);
    	return ResponseEntity.ok().body(dto);
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<LotacaoDTO > delete (@PathVariable long id){
    	
    	
    	service.delete(id);
    	return ResponseEntity.noContent().build();
    }
    
}
