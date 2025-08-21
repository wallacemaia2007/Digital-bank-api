package com.wallace.spring.boot.dto;

import com.wallace.spring.boot.model.entities.Cliente;

public record ClienteResponseDTO(Integer id, String nome, String cpf) {

	public ClienteResponseDTO(Cliente cliente) {
		this(cliente.getId(), cliente.getNome(), cliente.getCpf());
	}

}
