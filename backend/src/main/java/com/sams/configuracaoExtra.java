package com.sams;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configuracaoExtra {
	
	@Bean(name = "nameAplication")
 public String nomeAplicacao() {
	 
	 return "Sistema de Controle de Estoque";
	
       }	
	
}
