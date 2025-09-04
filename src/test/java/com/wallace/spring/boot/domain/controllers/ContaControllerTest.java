package com.wallace.spring.boot.domain.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallace.spring.boot.controller.ContaController;
import com.wallace.spring.boot.dto.ContaRequestDTO;
import com.wallace.spring.boot.dto.OperacaoRequestDTO;
import com.wallace.spring.boot.dto.TransferenciaRequestDTO;
import com.wallace.spring.boot.enums.TipoTransacao;
import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.exceptions.ContaInexistenteException;
import com.wallace.spring.boot.exceptions.DataInvalidaException;
import com.wallace.spring.boot.exceptions.GlobalExceptionHandler;
import com.wallace.spring.boot.exceptions.SaldoInsuficienteException;
import com.wallace.spring.boot.exceptions.TipoDeContaInvalidaException;
import com.wallace.spring.boot.exceptions.ValorMenorQueZeroException;
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

	private ObjectMapper mapper;

	private static final BigDecimal TAXA_PARA_TESTE = new BigDecimal("0.0089");

	Integer id = 1;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(contaController).setControllerAdvice(new GlobalExceptionHandler())
				.build();

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

		mapper = new ObjectMapper();
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

		mockMvc.perform(
				post("/contas").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(contaRequestDTO)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.tipoConta").value("ContaCorrente"));
	}

	@Test
	void deveSimularDeposito() throws Exception {
		OperacaoRequestDTO operacaoRequestDTO = new OperacaoRequestDTO(new BigDecimal(100), 1);
		contaCorrente.setSaldo(new BigDecimal(1100));

		when(contaService.depositar(operacaoRequestDTO.valor(), operacaoRequestDTO.contaId()))
				.thenReturn(contaCorrente);

		mockMvc.perform(put("/contas/{id}/deposito", contaCorrente.getId()).contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(operacaoRequestDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.saldo").value(1100));
	}

	@Test
	void deveSimularSaque() throws Exception {
		OperacaoRequestDTO operacaoRequestDTO = new OperacaoRequestDTO(new BigDecimal(100), 1);
		contaCorrente.setSaldo(new BigDecimal(900));

		when(contaService.sacar(operacaoRequestDTO.valor(), operacaoRequestDTO.contaId())).thenReturn(contaCorrente);

		mockMvc.perform(put("/contas/{id}/saque", contaCorrente.getId()).contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(operacaoRequestDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.saldo").value(900));
	}

	@Test
	void deveSimularTransferencia() throws Exception {
		TransferenciaRequestDTO dto = new TransferenciaRequestDTO(1, 2, new BigDecimal(100));
		List<Conta> contas = Arrays.asList(contaCorrente, contaPoupanca);

		when(contaService.transferir(dto.contaIdDepositar(), dto.valor(), dto.contaIdReceber())).thenReturn(contas);

		mockMvc.perform(
				put("/contas/transferencias").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[1].id").value(2));
	}

	@Test
	void deveSimularRendimento() throws Exception {
		LocalDate dataPrevista = LocalDate.now().plusMonths(2);
		BigDecimal valorEsperado = contaPoupanca.getSaldo().multiply(TAXA_PARA_TESTE).multiply(new BigDecimal(2));

		when(contaService.simularRendimento(dataPrevista, contaCorrente.getId())).thenReturn(valorEsperado);

		mockMvc.perform(get("/contas/{id}/simulacao-rendimento?data={data}", contaCorrente.getId(), dataPrevista))
				.andExpect(status().isOk()).andExpect(jsonPath("$.valor").value(valorEsperado.doubleValue()));
	}

	@Test
	void deveMostrarOHistoricoDaConta() throws Exception {
		BigDecimal valor = new BigDecimal(500);
		List<HistoricoConta> historico = Arrays.asList(
				new HistoricoConta(TipoTransacao.DEPOSITO, valor, contaPoupanca, contaCorrente),
				new HistoricoConta(TipoTransacao.SAQUE, valor, contaCorrente, contaPoupanca));

		when(historicoContaService.mostrarRegistrosTransacoes(contaCorrente.getId())).thenReturn(historico);

		mockMvc.perform(get("/contas/{id}/historico", contaCorrente.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].tipoDaTransacao").value("DEPOSITO"))
				.andExpect(jsonPath("$[0].valor").value(500))
				.andExpect(jsonPath("$[1].tipoDaTransacao").value("SAQUE"));
	}

	@Test
	void deveRetornar404AoBuscarContasPorClienteInexistente() throws Exception {
		String cpf = "100.100.200-23";

		when(clienteService.buscarClientePorCPF(cpf))
				.thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

		mockMvc.perform(get("/contas/clientes/{cpf}/contas", cpf).contentType(APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.message").value("Cliente não encontrado"))
				.andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.details").exists());
	}

	@Test
	void deveRetornar400AoCriarContaComTipoInválido() throws Exception {
		ContaRequestDTO contaRequestDTO = new ContaRequestDTO(1, "DD");

		when(contaService.criarConta(contaRequestDTO))
				.thenThrow(new TipoDeContaInvalidaException("Tipo de conta inválido"));

		mockMvc.perform(
				post("/contas").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(contaRequestDTO)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Tipo de conta inválido"))
				.andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.details").exists());
	}

	@Test
	void deveRetornar404AoTentarDepositarEmContaInexistente() throws Exception {
		BigDecimal valor = new BigDecimal(100);
		OperacaoRequestDTO dto = new OperacaoRequestDTO(valor, id);

		when(contaService.depositar(dto.valor(), dto.contaId()))
				.thenThrow(new ContaInexistenteException("Conta Inexistente"));

		mockMvc.perform(put("/contas/{id}/deposito", dto.contaId()).contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto))).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Conta Inexistente")).andExpect(jsonPath("$.timestamp").exists())
				.andExpect(jsonPath("$.details").exists());
	}

	@Test
	void deveRetornar400AoTentarSacarValorMaiorQueSaldo() throws Exception {
		BigDecimal valor = new BigDecimal(10000);
		OperacaoRequestDTO dto = new OperacaoRequestDTO(valor, id);

		when(contaService.sacar(valor, dto.contaId())).thenThrow(new SaldoInsuficienteException("Saldo Insuficiente"));

		mockMvc.perform(put("/contas/{id}/saque", dto.contaId()).contentType(APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto))).andExpect(status().isUnprocessableEntity())
				.andExpect(jsonPath("$.message").value("Saldo Insuficiente"))
				.andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.details").exists());
	}

	@Test
	void deveRetornar404AoTentarTransferirParaContaInexistente() throws Exception {
		BigDecimal valor = new BigDecimal(100);
		TransferenciaRequestDTO dto = new TransferenciaRequestDTO(contaCorrente.getId(), contaPoupanca.getId(), valor);

		when(contaService.transferir(dto.contaIdDepositar(), dto.valor(), dto.contaIdReceber()))
				.thenThrow(new ContaInexistenteException("Conta Inexistente"));

		mockMvc.perform(
				put("/contas/transferencias").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.message").value("Conta Inexistente"))
				.andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.details").exists());
	}

	@Test
	void deveRetornar400AoTentarTransferirValorNegativoOuZero() throws Exception {
		BigDecimal valor = new BigDecimal(-10);
		TransferenciaRequestDTO dto = new TransferenciaRequestDTO(contaCorrente.getId(), contaPoupanca.getId(), valor);

		when(contaService.transferir(dto.contaIdDepositar(), dto.valor(), dto.contaIdReceber()))
				.thenThrow(new ValorMenorQueZeroException("Valor menor que 0"));

		mockMvc.perform(
				put("/contas/transferencias").contentType(APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Valor menor que 0"))
				.andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.details").exists());
	}

	@Test
	void deveRetornar400AoSimularRendimentoComDataNoPassado() throws Exception {
		LocalDate dataPassada = LocalDate.now().minusYears(1);

		when(contaService.simularRendimento(dataPassada, id)).thenThrow(new DataInvalidaException("Data inválida"));

		mockMvc.perform(
				get("/contas/{id}/simulacao-rendimento?data={data}", id, dataPassada).contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value("Data inválida"))
				.andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.details").exists());
	}

	@Test
	void deveRetornar404AoSimularRendimentoEmContaNaoPoupanca() throws Exception {
		LocalDate data = LocalDate.now().plusYears(1);

		when(contaService.simularRendimento(data, contaCorrente.getId()))
				.thenThrow(new TipoDeContaInvalidaException("Conta não Poupança"));

		mockMvc.perform(get("/contas/{id}/simulacao-rendimento?data={data}", contaCorrente.getId(), data)
				.contentType(APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Conta não Poupança"))
				.andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.details").exists());
	}

	@Test
	void deveRetornar404AoBuscarHistoricoDeContaInexistente() throws Exception {
	    Integer idInexistente = 3;

	    when(historicoContaService.mostrarRegistrosTransacoes(idInexistente))
	            .thenThrow(new ContaInexistenteException("Conta inexistente"));

	    mockMvc.perform(get("/contas/{id}/historico", idInexistente)
	            .contentType(APPLICATION_JSON))
	            .andExpect(status().isNotFound())
	            .andExpect(jsonPath("$.message").value("Conta inexistente"))
	            .andExpect(jsonPath("$.timestamp").exists())
	            .andExpect(jsonPath("$.details").exists());
	}
}