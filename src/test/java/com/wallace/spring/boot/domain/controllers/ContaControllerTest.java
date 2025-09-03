package com.wallace.spring.boot.domain.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallace.spring.boot.controller.ContaController;
import com.wallace.spring.boot.dto.ContaRequestDTO;
import com.wallace.spring.boot.dto.OperacaoRequestDTO;
import com.wallace.spring.boot.dto.TransferenciaRequestDTO;
import com.wallace.spring.boot.enums.TipoTransacao;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.entities.Conta;
import com.wallace.spring.boot.model.entities.ContaCorrente;
import com.wallace.spring.boot.model.entities.ContaPoupanca;
import com.wallace.spring.boot.model.entities.HistoricoConta;
import com.wallace.spring.boot.model.services.ClienteService;
import com.wallace.spring.boot.model.services.ContaService;
import com.wallace.spring.boot.model.services.HistoricoContaService;

@ExtendWith(MockitoExtension.class)
public class ContaControllerTest {

	@Mock
	private ContaService contaService;

	@Mock
	private ClienteService clienteService;

	@Mock
	private HistoricoContaService historicoContaService;

	@InjectMocks
	private ContaController contaController;

	private MockMvc mockMvc;

	private Cliente cliente;
	private Conta contaCorrente;
	private Conta contaPoupanca;

	private static final BigDecimal TAXA_PARA_TESTE = new BigDecimal("0.0089");

	Integer id = 1;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(contaController).build();

		String cpf = "123.456.789-00";
		cliente = new Cliente("Wallace", cpf);
		cliente.setId(1);

		contaCorrente = new ContaCorrente();
		contaCorrente.setId(1);
		contaCorrente.setSaldo(new BigDecimal("1000.00"));
		contaCorrente.setCliente(cliente);

		contaPoupanca = new ContaPoupanca();
		contaPoupanca.setId(2);
		contaPoupanca.setSaldo(new BigDecimal("2000.00"));
		contaPoupanca.setCliente(cliente);
	}

	@Test
	@DisplayName("Deve retornar contas em JSON pelo endpoint")
	void deveRetornarContasPorCpfEndpoint() throws Exception {
		String cpf = cliente.getCpf();

		when(clienteService.buscarClientePorCPF(cpf)).thenReturn(cliente);
		when(contaService.buscarContasPorCliente(cliente)).thenReturn(Arrays.asList(contaCorrente, contaPoupanca));

		mockMvc.perform(get("/contas/clientes/{cpf}/contas", cpf)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].tipoConta").value("ContaCorrente"))
				.andExpect(jsonPath("$[1].id").value(2)).andExpect(jsonPath("$[1].tipoConta").value("ContaPoupanca"));
	}

	@Test
	void deveCriarUmaContaCorrente() throws Exception {
		ContaRequestDTO contaRequestDTO = new ContaRequestDTO(1, "CC");

		when(contaService.criarConta(any(ContaRequestDTO.class))).thenReturn(contaCorrente);

		mockMvc.perform(post("/contas").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(contaRequestDTO))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.tipoConta").value("ContaCorrente"));
	}

	@Test
	void deveSimularDeposito() throws Exception {

		OperacaoRequestDTO operacaoRequestDTO = new OperacaoRequestDTO(new BigDecimal(100), 1);

		contaCorrente.setSaldo(new BigDecimal(1100));
		
		when(contaService.depositar(operacaoRequestDTO.valor(), operacaoRequestDTO.contaId()))
				.thenReturn(contaCorrente);

		mockMvc.perform(put("/contas/{id}/deposito", contaCorrente.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(operacaoRequestDTO))).andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.saldo").value(1100));

	}

	@Test
	void deveSimularSaque() throws Exception {

		OperacaoRequestDTO operacaoRequestDTO = new OperacaoRequestDTO(new BigDecimal(100), 1);
		
		contaCorrente.setSaldo(new BigDecimal(900));

		when(contaService.sacar(operacaoRequestDTO.valor(), operacaoRequestDTO.contaId())).thenReturn(contaCorrente);

		mockMvc.perform(put("/contas/{id}/saque", contaCorrente.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(operacaoRequestDTO))).andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.saldo").value(900));


	}

	@Test
	void deveSimularTransferencia() throws Exception {

		TransferenciaRequestDTO transferenciaRequestDTO = new TransferenciaRequestDTO(1, 2, new BigDecimal(100));

		List<Conta> contas = Arrays.asList(contaCorrente, contaPoupanca);

		when(contaService.transferir(transferenciaRequestDTO.contaIdDepositar(), transferenciaRequestDTO.valor(),
				transferenciaRequestDTO.contaIdReceber())).thenReturn(contas);

		mockMvc.perform(put("/contas/transferencias")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(new ObjectMapper().writeValueAsString(transferenciaRequestDTO)))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$[0].id").value(1))
		        .andExpect(jsonPath("$[1].id").value(2));


	}

	@Test
	void deveSimularRendimento() throws Exception {
		LocalDate dataPrevista = LocalDate.now().plusMonths(2);

		BigDecimal valorEsperado = contaPoupanca.getSaldo().multiply(TAXA_PARA_TESTE).multiply(new BigDecimal(2));

		when(contaService.simularRendimento(dataPrevista, contaCorrente.getId())).thenReturn(valorEsperado);

		mockMvc.perform(get("/contas/{id}/simulacao-rendimento?data={data}", contaCorrente.getId(), dataPrevista))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.valor").value(valorEsperado.doubleValue()));

	}

	@Test
	void deveMostrarOHistoricoDaConta() throws Exception {
		BigDecimal valor = new BigDecimal(500);

		List<HistoricoConta> historicoConta = Arrays.asList(
				new HistoricoConta(TipoTransacao.DEPOSITO, valor, contaPoupanca, contaCorrente),
				new HistoricoConta(TipoTransacao.SAQUE, valor, contaCorrente, contaPoupanca));

		when(historicoContaService.mostrarRegistrosTransacoes(contaCorrente.getId())).thenReturn(historicoConta);

		mockMvc.perform(get("/contas/{id}/historico", contaCorrente.getId())).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].tipoDaTransacao").value("DEPOSITO"))
		.andExpect(jsonPath("$[0].valor").value(500))
		.andExpect(jsonPath("$[1].tipoDaTransacao").value("SAQUE"));
	}

}
