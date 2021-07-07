package com.sams.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sams.entities.Veiculo;

public class VeiculoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private Long id;
	 @NotBlank(message = "o campo é obrigatório")
	  private Integer ano; 
	 @Size(min = 7, max = 7, message = "o tamanho da placa deve ser de 7 characteres, sendo os três primeiros letras.")
	 @NotBlank(message = "o campo é obrigatório")
	  private String placa;
	 @Size(min = 9, max = 9, message = "o tamanho do renavam deve ser de 9 characteres.")
	 @NotBlank(message = "o campo é obrigatório")
	  private String renavam;
	 @NotBlank(message = "o campo é obrigatório")
	  private String patrimonio;
	 @NotBlank(message = "o campo é obrigatório")
	  private String chassi;
	 @NotBlank(message = "o campo é obrigatório")
	  private String versao;
	 @NotBlank(message = "o campo é obrigatório")
	  private String capacidadeTanque;
	 @NotBlank(message = "o campo é obrigatório")
	  private String tipoCombustivel;
	  public VeiculoDTO() {

	}
	  
	  
	  
	public VeiculoDTO(Long id, Integer ano, String placa, String renavam, String patrimonio, String chassi,
			String versao, String capacidadeTanque, String tipoCombustivel) {
		super();
		this.id = id;
		
		this.ano = ano;
		this.placa = placa;
		this.renavam = renavam;
		this.patrimonio = patrimonio;
		this.chassi = chassi;
		this.versao = versao;
		this.capacidadeTanque = capacidadeTanque;
		this.tipoCombustivel = tipoCombustivel;
	}
	
	public VeiculoDTO(Veiculo entity) {
		
		this.id = entity.getId();
		this.placa = entity.getPlaca();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getRenavam() {
		return renavam;
	}
	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}
	public String getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(String patrimonio) {
		this.patrimonio = patrimonio;
	}
	public String getChassi() {
		return chassi;
	}
	public void setChassi(String chassi) {
		this.chassi = chassi;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public String getCapacidadeTanque() {
		return capacidadeTanque;
	}
	public void setCapacidadeTanque(String capacidadeTanque) {
		this.capacidadeTanque = capacidadeTanque;
	}
	public String getTipoCombustivel() {
		return tipoCombustivel;
	}
	public void setTipoCombustivel(String tipoCombustivel) {
		this.tipoCombustivel = tipoCombustivel;
	}
	  

}
