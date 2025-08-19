package com.wallace.spring.boot.model.services;

import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.repository.ClienteRepository;

public class ClienteService {
	
	private ClienteRepository clienterepository;
	
	public Cliente acharClientePorCPF(String cpf) {
		return clienterepository.findByCpf(cpf)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado")) ;
		
	}
	 public void alterarDadosClientePorCPF(String cpfAtual, String nomeNovo, String cpfNovo) {
	        Cliente cliente = acharClientePorCPF(cpfAtual);

	        cliente.setNome(nomeNovo);
	        cliente.setCpf(cpfNovo);

	        clienterepository.save(cliente);
	    }

}
