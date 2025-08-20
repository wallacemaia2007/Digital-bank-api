package com.wallace.spring.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallace.spring.boot.dto.ContaRequestDTO;
import com.wallace.spring.boot.dto.ContaResponseDTO;
import com.wallace.spring.boot.dto.OperacaoRequestDTO;
import com.wallace.spring.boot.dto.TransferenciaRequestDTO;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.entities.Conta;
import com.wallace.spring.boot.model.services.ClienteService;
import com.wallace.spring.boot.model.services.ContaService;

@RestController
@RequestMapping("/contas")
public class ContaController {
	
	@Autowired
	private ContaService contaService;

	@Autowired
	private ClienteService clienteService;

	
	@GetMapping(path = "/{cpf}")
	public ResponseEntity<List<ContaResponseDTO>> buscarContasPorCpf(@PathVariable String cpf){
		Cliente cliente = clienteService.buscarClientePorCPF(cpf);
		List<Conta> contas = contaService.buscarContasPorCliente(cliente);
		List<ContaResponseDTO> contasResponseDTO = contas.stream().map(ContaResponseDTO::new).toList();
		return ResponseEntity.ok(contasResponseDTO);
	}
	
	@PostMapping(path = "/criar")
	public ResponseEntity<ContaResponseDTO> criarContaPorCpf(@RequestBody ContaRequestDTO contaRequestDTO){
		
		Conta conta = contaService.criarConta(contaRequestDTO);
		ContaResponseDTO contaResponseDTO = new ContaResponseDTO(conta);
	    return ResponseEntity.status(HttpStatus.CREATED).body(contaResponseDTO);
	}
	
	@PutMapping(path = "/depositar")
	public ResponseEntity<ContaResponseDTO> depositar(@RequestBody OperacaoRequestDTO operacaoRequestDTO){
		Conta conta =  contaService.depositar(operacaoRequestDTO.getValor(), operacaoRequestDTO.getContaId());
		ContaResponseDTO contaResponseDTO = new ContaResponseDTO(conta);
	    return ResponseEntity.ok(contaResponseDTO);		
	}
	
	@PutMapping(path = "/sacar")
	public ResponseEntity<ContaResponseDTO> sacar(@RequestBody OperacaoRequestDTO operacaoRequestDTO){
		Conta conta =  contaService.sacar(operacaoRequestDTO.getValor(), operacaoRequestDTO.getContaId());
		ContaResponseDTO contaResponseDTO = new ContaResponseDTO(conta);
		return ResponseEntity.ok(contaResponseDTO);		
	}
	@PutMapping(path = "/transferir")
	public ResponseEntity<List<ContaResponseDTO>> transferir(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO){
		List<Conta> contas =  contaService.transferir(transferenciaRequestDTO.getContaIdEntrada(),
				transferenciaRequestDTO.getValor(),
				transferenciaRequestDTO.getContaIdSaida());
		
		List<ContaResponseDTO> contasResponseDTO = contas.stream().map(ContaResponseDTO::new).toList();
		
		return ResponseEntity.ok(contasResponseDTO);
	}



	
}
