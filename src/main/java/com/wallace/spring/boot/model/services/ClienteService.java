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
	private ClienteRepository clienteRepository;

	// GET/cpf
	public Cliente buscarClientePorCPF(String cpf) {
		return clienteRepository.findByCpf(cpf)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));
	}

	// GET
	public List<Cliente> buscarTodosClientes() {
		return clienteRepository.findAll();
	}

	// POST
	@Transactional
	public Cliente cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
		if (clienteRepository.findByCpf(clienteRequestDTO.cpf()).isPresent()) {
			throw new CpfJaExistenteException("CPF já cadastrado no sistema.");
		}
		Cliente novoCliente = new Cliente(clienteRequestDTO.nome(),
				clienteRequestDTO.cpf());
		clienteRepository.save(novoCliente);
		return novoCliente;
	}

	// PUT/cpf
	@Transactional
	public Cliente alterarDadosClientePorCPF(String cpf, ClienteRequestDTO clienteRequestDTO) {
		Cliente cliente = buscarClientePorCPF(cpf);
		if (!cliente.getCpf().equals(clienteRequestDTO.cpf())
				&& clienteRepository.findByCpf(clienteRequestDTO.cpf()).isPresent()) {
			throw new CpfJaExistenteException("CPF já cadastrado no sistema.");
		}
		cliente.setNome(clienteRequestDTO.nome());
		cliente.setCpf(clienteRequestDTO.cpf());

		clienteRepository.save(cliente);
		return cliente;
	}

	// DELETE/cpf
	@Transactional
	public void deletarClientePorCpf(String cpf) {
		Cliente cliente = buscarClientePorCPF(cpf);
		clienteRepository.delete(cliente);
	}

}
