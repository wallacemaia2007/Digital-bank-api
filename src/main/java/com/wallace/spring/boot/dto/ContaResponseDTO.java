package com.wallace.spring.boot.dto;

import java.math.BigDecimal;

import com.wallace.spring.boot.model.entities.Conta;

public record ContaResponseDTO(Integer id, String tipoConta, BigDecimal saldo, ClienteResponseDTO cliente) {
	
	public ContaResponseDTO(Conta conta) {
		this(conta.getId(),conta.getClass().getSimpleName(), conta.getSaldo(),new ClienteResponseDTO(conta.getCliente()));
	}
}