package com.wallace.spring.boot.dto;

import com.wallace.spring.boot.model.entities.Cliente;

public class ClienteResponseDTO {
	
	private Integer id;
	private String nome;
	private String cpf;
	
	public ClienteResponseDTO(Cliente cliente) {
		id = cliente.getId();
		nome = cliente.getNome();
		cpf = cliente.getCpf();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	

}
