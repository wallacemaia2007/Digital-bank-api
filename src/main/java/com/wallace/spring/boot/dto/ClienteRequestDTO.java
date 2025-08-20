package com.wallace.spring.boot.dto;

public class ClienteRequestDTO {
	
	private String nome;
	private String cpf;
	
	
	public ClienteRequestDTO() {
		super();
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
