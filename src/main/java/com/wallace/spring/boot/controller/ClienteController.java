package com.wallace.spring.boot.controller;

import java.util.List;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService) {
    	this.clienteService = clienteService;
    }

    @Operation(
        summary = "Buscar todos os clientes",
        description = "Retorna os dados de todos os clientes cadastrados no sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso.")
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> buscarTodosClientes() {
        List<Cliente> clientes = clienteService.buscarTodosClientes();
        List<ClienteResponseDTO> clientesResponseDTO = clientes.stream().map(ClienteResponseDTO::new).toList();
        return ResponseEntity.ok(clientesResponseDTO);
    }

    @Operation(
        summary = "Buscar cliente por CPF",
        description = "Retorna os dados de um cliente específico a partir do CPF informado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado para o CPF informado.")
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorCPF(
        @Parameter(description = "CPF do cliente a ser buscado", required = true, example = "123.456.789-00")
        @PathVariable String cpf) {
        
        Cliente cliente = clienteService.buscarClientePorCPF(cpf);
        return ResponseEntity.ok(new ClienteResponseDTO(cliente));
    }

    @Operation(
        summary = "Cadastrar novo cliente",
        description = "Cria um novo cliente no sistema com base nos dados fornecidos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso."),
        @ApiResponse(responseCode = "409", description = "O CPF informado já está cadastrado.")
    })
    @PostMapping(path = "/cadastrar")
    public ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        Cliente novoCliente = clienteService.cadastrarCliente(clienteRequestDTO);
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO(novoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
    }

    @Operation(
        summary = "Atualizar dados do cliente",
        description = "Altera o nome e/ou CPF de um cliente existente, identificado pelo CPF atual."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dados do cliente atualizados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado para o CPF informado."),
        @ApiResponse(responseCode = "409", description = "O novo CPF informado já está cadastrado em outro cliente.")
    })
    @PutMapping("/alterar/{cpf}")
    public ResponseEntity<ClienteResponseDTO> alterarDadosClientePorCPF(
        @Parameter(description = "CPF atual do cliente que será alterado", required = true, example = "123.456.789-00")
        @PathVariable String cpf,
        @RequestBody ClienteRequestDTO clienteRequestDTO) {
        
        Cliente clienteAtualizado = clienteService.alterarDadosClientePorCPF(cpf, clienteRequestDTO);
        ClienteResponseDTO responseDTO = new ClienteResponseDTO(clienteAtualizado);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
        summary = "Deletar cliente por CPF",
        description = "Remove permanentemente um cliente do sistema a partir do CPF informado."
    )
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado para o CPF informado.")
    })
    @DeleteMapping("/deletar/{cpf}")
    public ResponseEntity<Void> deletarClientePorCpf(
        @Parameter(description = "CPF do cliente que será removido", required = true, example = "123.456.789-00")
        @PathVariable String cpf) {
        
        clienteService.deletarClientePorCpf(cpf);
        return ResponseEntity.noContent().build();
    }
}
