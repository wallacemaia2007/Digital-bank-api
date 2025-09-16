package com.wallace.spring.boot.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	private ClienteRepository clienteRepository;

	// GET/cpf
	public Cliente buscarClientePorCPF(String cpf) {
        logger.info("Buscando cliente pelo CPF: {}", cpf);
		return clienteRepository.findByCpf(cpf)
				.orElseThrow(() -> {
                    logger.warn("Cliente não encontrado para o CPF: {}", cpf);
                    return new ClienteNaoEncontradoException("Cliente não encontrado");
                });
	}

	// GET
	public List<Cliente> buscarTodosClientes() {
        logger.info("Buscando todos os clientes");
		return clienteRepository.findAll();
	}

	// POST
	@Transactional
	public Cliente cadastrarCliente(ClienteRequestDTO clienteRequestDTO) {
        logger.info("Iniciando cadastro de novo cliente com CPF: {}", clienteRequestDTO.cpf());
		if (clienteRepository.findByCpf(clienteRequestDTO.cpf()).isPresent()) {
            logger.warn("Tentativa de cadastrar CPF já existente: {}", clienteRequestDTO.cpf());
			throw new CpfJaExistenteException("CPF já cadastrado no sistema.");
		}
		Cliente novoCliente = new Cliente(clienteRequestDTO.nome(), clienteRequestDTO.cpf());
		clienteRepository.save(novoCliente);
        logger.info("Cliente com CPF {} cadastrado com sucesso. ID: {}", novoCliente.getCpf(), novoCliente.getId());
		return novoCliente;
	}

	// PUT/cpf
	@Transactional
	public Cliente alterarDadosClientePorCPF(String cpf, ClienteRequestDTO clienteRequestDTO) {
        logger.info("Iniciando alteração de dados para o cliente com CPF: {}", cpf);
		Cliente cliente = buscarClientePorCPF(cpf);
		if (!cliente.getCpf().equals(clienteRequestDTO.cpf())
				&& clienteRepository.findByCpf(clienteRequestDTO.cpf()).isPresent()) {
            logger.warn("Tentativa de alterar para um CPF já existente: {}", clienteRequestDTO.cpf());
			throw new CpfJaExistenteException("CPF já cadastrado no sistema.");
		}
		cliente.setNome(clienteRequestDTO.nome());
		cliente.setCpf(clienteRequestDTO.cpf());

		clienteRepository.save(cliente);
        logger.info("Dados do cliente com CPF {} atualizados com sucesso.", cpf);
		return cliente;
	}

	// DELETE/cpf
	@Transactional
	public void deletarClientePorCpf(String cpf) {
        logger.info("Iniciando exclusão do cliente com CPF: {}", cpf);
		Cliente cliente = buscarClientePorCPF(cpf);
		clienteRepository.delete(cliente);
        logger.info("Cliente com CPF {} deletado com sucesso.", cpf);
	}
}