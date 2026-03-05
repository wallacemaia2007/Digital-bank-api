package com.wallace.spring.boot.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.wallace.spring.boot.services.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Operation(summary = "Buscar todos os clientes")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso."),
			@ApiResponse(responseCode = "401", description = "Realize o Login para acessar os endpoints!")
})
	@GetMapping
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<List<ClienteResponseDTO>> buscarTodosClientes() {
		List<Cliente> clientes = clienteService.buscarTodosClientes();
		List<ClienteResponseDTO> clientesResponseDTO = clientes.stream().map(ClienteResponseDTO::new).toList();
		return ResponseEntity.ok(clientesResponseDTO);
	}

	@Operation(summary = "Buscar cliente por CPF")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso."),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado para o CPF informado.") })
	@GetMapping("/{cpf}")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity<ClienteResponseDTO> buscarClientePorCPF(
			@Parameter(description = "CPF do cliente a ser buscado", required = true, example = "123.456.789-00") @PathVariable String cpf) {

		Cliente cliente = clienteService.buscarClientePorCPF(cpf);
		return ResponseEntity.ok(new ClienteResponseDTO(cliente));
	}

	@Operation(summary = "Cadastrar novo cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso."),
			@ApiResponse(responseCode = "409", description = "O CPF informado já está cadastrado.") })
	@PostMapping
	@PreAuthorize("hasAuthority('user:write')")
	public ResponseEntity<ClienteResponseDTO> cadastrar(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
		Cliente novoCliente = clienteService.cadastrarCliente(clienteRequestDTO);
		ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(novoCliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
	}

	@Operation(summary = "Atualizar dados do cliente", description = "Apenas administradores podem alterar dados de clientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Dados do cliente atualizados com sucesso."),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado para o CPF informado."),
			@ApiResponse(responseCode = "409", description = "O novo CPF informado já está cadastrado em outro cliente.") })
	@PutMapping("/{cpf}")
	@PreAuthorize("hasAuthority('admin:update')") 
	public ResponseEntity<ClienteResponseDTO> alterarDadosClientePorCPF(
			@Parameter(description = "CPF atual do cliente que será alterado", required = true, example = "123.456.789-00") @PathVariable String cpf,
			@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {

		Cliente clienteAtualizado = clienteService.alterarDadosClientePorCPF(cpf, clienteRequestDTO);
		ClienteResponseDTO responseDTO = new ClienteResponseDTO(clienteAtualizado);
		return ResponseEntity.ok(responseDTO);
	}

	@Operation(summary = "Deletar cliente por CPF", description = "Apenas administradores podem deletar clientes")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso."),
			@ApiResponse(responseCode = "404", description = "Cliente não encontrado para o CPF informado."),
			@ApiResponse(responseCode = "403", description = "Acesso negado - Apenas administradores") })
	@DeleteMapping("/{cpf}")
	@PreAuthorize("hasAuthority('admin:delete')")
	public ResponseEntity<Void> deletarClientePorCpf(
			@Parameter(description = "CPF do cliente que será removido", required = true, example = "123.456.789-00") @PathVariable String cpf) {

		clienteService.deletarClientePorCpf(cpf);
		return ResponseEntity.noContent().build();
	}
}