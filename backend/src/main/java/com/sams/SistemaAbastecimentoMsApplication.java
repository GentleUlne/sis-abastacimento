package com.sams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class SistemaAbastecimentoMsApplication {
	@Autowired
	@Qualifier("nameAplication")
	private String nameAplication;
	
	
	@GetMapping("/nome")
 public String nomeAplicacao() {
	 
		 return "Sistema de Controle de Estoque";
	
 }
		
	
	
	public static void main(String[] args) {
		SpringApplication.run(SistemaAbastecimentoMsApplication.class, args);
	}

}
