package com.wallace.spring.boot.dto;

import java.math.BigDecimal;

import com.wallace.spring.boot.model.entities.Conta;

public class ContaResponseDTO {
	
	private Integer id;
	private String tipoConta;
	private BigDecimal saldo;	
	private ClienteResponseDTO cliente; 

	public ContaResponseDTO(Conta conta) {
		this.id = conta.getId();
		this.tipoConta = conta.getClass().getSimpleName();
		this.saldo = conta.getSaldo();
		this.cliente = new ClienteResponseDTO(conta.getCliente());
	}

	public Integer getId() {
		return id;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public ClienteResponseDTO getCliente() {
		return cliente;
	}
}