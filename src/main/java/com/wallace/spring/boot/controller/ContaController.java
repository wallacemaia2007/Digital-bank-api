package com.wallace.spring.boot.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wallace.spring.boot.dto.ContaRequestDTO;
import com.wallace.spring.boot.dto.ContaResponseDTO;
import com.wallace.spring.boot.dto.OperacaoRequestDTO;
import com.wallace.spring.boot.dto.RendimentoResponseDTO;
import com.wallace.spring.boot.dto.TransferenciaRequestDTO;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.entities.Conta;
import com.wallace.spring.boot.model.services.ClienteService;
import com.wallace.spring.boot.model.services.ContaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/contas")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@Autowired
	private ClienteService clienteService;

	@Operation(
		    summary = "Buscar contas por CPF",
		    description = "Retorna todas as contas associadas a um cliente a partir do CPF fornecido."
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Contas encontradas com sucesso."),
		    @ApiResponse(responseCode = "404", description = "Cliente não encontrado para o CPF fornecido.")
		})

	@GetMapping(path = "/{cpf}")
	public ResponseEntity<List<ContaResponseDTO>> buscarContasPorCpf(@PathVariable String cpf) {
		Cliente cliente = clienteService.buscarClientePorCPF(cpf);
		List<Conta> contas = contaService.buscarContasPorCliente(cliente);
		List<ContaResponseDTO> contasResponseDTO = contas.stream().map(ContaResponseDTO::new).toList();
		return ResponseEntity.ok(contasResponseDTO);
	}

	@Operation(
		    summary = "Cria uma nova conta",
		    description = "Cria uma nova conta para um cliente existente a partir de seu ID. Cada cliente pode ter apenas uma conta de cada tipo (Corrente ou Poupança)."
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "201", description = "Conta criada com sucesso."),
		    @ApiResponse(responseCode = "400", description = "Tipo de conta inválido."),
		    @ApiResponse(responseCode = "404", description = "Cliente não encontrado."),
		    @ApiResponse(responseCode = "409", description = "O cliente já possui uma conta desse tipo.")
		})
	@PostMapping(path = "/criar")
	public ResponseEntity<ContaResponseDTO> criarContaPorCpf(@RequestBody ContaRequestDTO contaRequestDTO) {

		Conta conta = contaService.criarConta(contaRequestDTO);
		ContaResponseDTO contaResponseDTO = new ContaResponseDTO(conta);
		return ResponseEntity.status(HttpStatus.CREATED).body(contaResponseDTO);
	}

	@Operation(
		    summary = "Depositar saldo na conta",
		    description = "Deposita um valor em uma conta Corrente ou Poupança."
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso."),
		    @ApiResponse(responseCode = "400", description = "Valor informado é negativo ou igual a zero."),
		    @ApiResponse(responseCode = "404", description = "Conta não encontrada pelo ID fornecido.")
		})
	@PutMapping(path = "/depositar")
	public ResponseEntity<ContaResponseDTO> depositar(@RequestBody OperacaoRequestDTO operacaoRequestDTO) {
		Conta conta = contaService.depositar(operacaoRequestDTO.valor(), operacaoRequestDTO.contaId());
		ContaResponseDTO contaResponseDTO = new ContaResponseDTO(conta);
		return ResponseEntity.ok(contaResponseDTO);
	}

	@Operation(
		    summary = "Realizar saque",
		    description = "Saca um valor de uma conta Corrente ou Poupança."
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Saque realizado com sucesso."),
		    @ApiResponse(responseCode = "400", description = "Valor informado é negativo ou igual a zero."),
		    @ApiResponse(responseCode = "404", description = "Conta não encontrada pelo ID fornecido."),
		    @ApiResponse(responseCode = "409", description = "Saldo insuficiente para realizar o saque.")
		})
	@PutMapping(path = "/sacar")
	public ResponseEntity<ContaResponseDTO> sacar(@RequestBody OperacaoRequestDTO operacaoRequestDTO) {
		Conta conta = contaService.sacar(operacaoRequestDTO.valor(), operacaoRequestDTO.contaId());
		ContaResponseDTO contaResponseDTO = new ContaResponseDTO(conta);
		return ResponseEntity.ok(contaResponseDTO);
	}

	@Operation(
		    summary = "Transferir saldo entre contas",
		    description = "Transfere um valor de uma conta de origem para uma conta de destino."
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso."),
		    @ApiResponse(responseCode = "400", description = "Valor informado é negativo ou igual a zero."),
		    @ApiResponse(responseCode = "404", description = "Conta de origem ou destino não encontrada."),
		    @ApiResponse(responseCode = "409", description = "Saldo insuficiente na conta de origem para realizar a transferência.")
		})
	@PutMapping(path = "/transferir")
	public ResponseEntity<List<ContaResponseDTO>> transferir(
			@RequestBody TransferenciaRequestDTO transferenciaRequestDTO) {
		List<Conta> contas = contaService.transferir(transferenciaRequestDTO.contaIdDepositar(),
				transferenciaRequestDTO.valor(), transferenciaRequestDTO.contaIdReceber());

		List<ContaResponseDTO> contasResponseDTO = contas.stream().map(ContaResponseDTO::new).toList();

		return ResponseEntity.ok(contasResponseDTO);
	}

	@Operation(
		    summary = "Simular rendimento de uma conta poupança",
		    description = "Simula o rendimento futuro de uma conta Poupança a partir de uma data prevista."
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso."),
		    @ApiResponse(responseCode = "400", description = "Data inválida fornecida."),
		    @ApiResponse(responseCode = "404", description = "Conta não encontrada ou não é do tipo Poupança.")
		})
	@GetMapping(path = "/simular/{id}")
	public ResponseEntity<RendimentoResponseDTO> simularRendimento(@PathVariable("id") Integer id,
			@Parameter(example = "2026-10-20") @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPrevista) {

		BigDecimal valorSimulado = contaService.simularRendimento(dataPrevista, id);
		RendimentoResponseDTO rendimentoResponseDTO = new RendimentoResponseDTO(valorSimulado);

		return ResponseEntity.ok(rendimentoResponseDTO);
	}

}
