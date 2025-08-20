package com.wallace.spring.boot.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallace.spring.boot.dto.ClienteRequestDTO;
import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.exceptions.CpfJaExistenteException;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienterepository;

	//GET/cpf
	public Cliente buscarClientePorCPF(String cpf) {
		return clienterepository.findByCpf(cpf)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));
	}
	
	//GET
	public List<Cliente> buscarTodosClientes() {
		return clienterepository.findAll();
	}

	//POST
	@Transactional
	public Cliente cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
		if (clienterepository.findByCpf(clienteRequestDTO.getCpf()).isPresent()) {
			throw new CpfJaExistenteException("CPF já cadastrado no sistema.");
		}
		Cliente novoCliente = new Cliente(clienteRequestDTO.getNome(), clienteRequestDTO.getCpf());
		clienterepository.save(novoCliente);
		return novoCliente;
	}
	
	//PUT/cpf
	@Transactional
	public Cliente alterarDadosClientePorCPF(String cpf,ClienteRequestDTO clienteRequestDTO) {
		Cliente cliente = buscarClientePorCPF(cpf);
		if (!cliente.getCpf().equals(clienteRequestDTO.getCpf()) && clienterepository.findByCpf(clienteRequestDTO.getCpf()).isPresent()) {
			throw new CpfJaExistenteException("CPF já cadastrado no sistema.");
		}
		cliente.setNome(clienteRequestDTO.getNome());
		cliente.setCpf(clienteRequestDTO.getCpf());

		clienterepository.save(cliente);
		return cliente;
	}
	
	//DELETE/cpf
	@Transactional
	public void deletarClientePorCpf(String cpf) {
		Cliente cliente = buscarClientePorCPF(cpf);
		clienterepository.delete(cliente);
	}

}
