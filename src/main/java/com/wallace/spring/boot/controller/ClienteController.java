package com.wallace.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallace.spring.boot.dto.ClienteRequestDTO;
import com.wallace.spring.boot.dto.ClienteResponseDTO;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<ClienteResponseDTO>> buscarTodosClientes() {
		List<Cliente> clientes = clienteService.buscarTodosClientes();
		List<ClienteResponseDTO> clientesResponseDTO = clientes.stream().map(ClienteResponseDTO::new).toList();
		return ResponseEntity.ok(clientesResponseDTO);

	}

	@GetMapping(path = "/{cpf}")
	public ResponseEntity<ClienteResponseDTO> buscarClientePorCPF(@PathVariable String cpf) {
		Cliente cliente = clienteService.buscarClientePorCPF(cpf);
		ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(cliente);
		return ResponseEntity.ok(clienteResponseDTO);
	}

	@PostMapping(path = "/cadastrar")
	public ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody ClienteRequestDTO clienteRequestDTO) {
		Cliente novoCliente = clienteService.cadastrarCliente(clienteRequestDTO);
		ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(novoCliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
	}

	@PutMapping("/alterar/{cpf}") 
	public ResponseEntity<ClienteResponseDTO> alterarDadosClientePorCPF(@PathVariable String cpf, @RequestBody ClienteRequestDTO clienteRequestDTO) {
	    Cliente clienteAtualizado = clienteService.alterarDadosClientePorCPF(cpf, clienteRequestDTO);
	    ClienteResponseDTO responseDTO = new ClienteResponseDTO(clienteAtualizado);
	    return ResponseEntity.ok(responseDTO);
	}
	
	@DeleteMapping("/deletar/{cpf}")
	public ResponseEntity<Void> deletarClientePorCpf(@PathVariable String cpf){
		clienteService.deletarClientePorCpf(cpf);
		return ResponseEntity.noContent().build();
	}

}
