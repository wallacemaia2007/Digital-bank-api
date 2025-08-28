package com.wallace.spring.boot.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wallace.spring.boot.dto.ContaRequestDTO;
import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.exceptions.ContaInexistenteException;
import com.wallace.spring.boot.exceptions.ContaJaExistenteException;
import com.wallace.spring.boot.exceptions.DataInvalidaException;
import com.wallace.spring.boot.exceptions.SaldoInsuficienteException;
import com.wallace.spring.boot.exceptions.TipoDeContaInvalidaException;
import com.wallace.spring.boot.exceptions.ValorMenorQueZeroException;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.entities.Conta;
import com.wallace.spring.boot.model.entities.ContaCorrente;
import com.wallace.spring.boot.model.entities.ContaPoupanca;
import com.wallace.spring.boot.model.repository.ClienteRepository;
import com.wallace.spring.boot.model.repository.ContaRepository;
import com.wallace.spring.boot.model.services.ContaService;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private ContaRepository contaRepository;

	private ContaService contaService;

	private Conta conta;
	
    private static final BigDecimal TAXA_PARA_TESTE = new BigDecimal("0.0089");

	@BeforeEach
	void setUp() {
		contaService = new ContaService(clienteRepository, contaRepository, TAXA_PARA_TESTE);

		conta = new ContaCorrente();
		conta.setId(1);
		conta.setSaldo(new BigDecimal(1000));
	}

	@Test
	@DisplayName("Deve depositar com sucesso")
	void DeveDepositarComSucesso() {

		Integer id = 1;
		BigDecimal valor = new BigDecimal(200);
		BigDecimal valorEsperado = new BigDecimal(1200);

		when(contaRepository.findById(id)).thenReturn(Optional.of(conta));

		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta novaConta = contaService.depositar(valor, id);

		assertNotNull(novaConta);
		assertEquals(valorEsperado, novaConta.getSaldo());

		verify(contaRepository, times(1)).findById(id);
		verify(contaRepository, times(1)).save(conta);
	}

	@Test
	@DisplayName("Deve lancar uma excecao ao depositar um valor menor ou igual a zero")
	void deveLancarExcecaoAoDepositarValorMenorOuIgualAZero() {

		Integer id = 1;
		BigDecimal valor = new BigDecimal(-1);

		when(contaRepository.findById(id)).thenReturn(Optional.of(conta));

		assertThrows(ValorMenorQueZeroException.class, () -> contaService.depositar(valor, id));

		verify(contaRepository, never()).save(any(Conta.class));
	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar depositar em uma conta com Id inválido")
	void DeveLançarExceçãoAoTentarDepositarEmUmaContaComIdInválido() {

		Integer id = 1;
		BigDecimal valor = new BigDecimal(200);

		when(contaRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(ContaInexistenteException.class, () -> contaService.depositar(valor, id));

		verify(contaRepository, never()).save(any(Conta.class));

	}

	@Test
	@DisplayName("Deve sacar com sucesso")
	void DeveSacarComSucesso() {

		Integer id = 1;
		BigDecimal valor = new BigDecimal(200);
		BigDecimal valorEsperado = new BigDecimal(800);

		when(contaRepository.findById(id)).thenReturn(Optional.of(conta));

		when(contaRepository.save(any(Conta.class))).thenReturn(conta);

		Conta novaConta = contaService.sacar(valor, id);

		assertNotNull(novaConta);
		assertEquals(valorEsperado, novaConta.getSaldo());

		verify(contaRepository, times(1)).findById(id);
		verify(contaRepository, times(1)).save(conta);
	}

	@Test
	@DisplayName("Deve lancar uma excecao ao sacar um valor menor ou igual a zero")
	void deveLancarExcecaoAoSacarValorMenorOuIgualAZero() {

		Integer id = 1;
		BigDecimal valor = new BigDecimal(-1);

		when(contaRepository.findById(id)).thenReturn(Optional.of(conta));

		assertThrows(ValorMenorQueZeroException.class, () -> contaService.sacar(valor, id));

		verify(contaRepository, never()).save(any(Conta.class));
	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar sacar em uma conta com Id inválido")
	void DeveLançarExceçãoAoTentarSacarEmUmaContaComIdInválido() {

		Integer id = 1;
		BigDecimal valor = new BigDecimal(200);

		when(contaRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(ContaInexistenteException.class, () -> contaService.sacar(valor, id));

		verify(contaRepository, never()).save(any(Conta.class));

	}

	@Test
	@DisplayName("Deve lancar uma exceção ao tentar sacar com saldo insuficiente na conta")
	void DeveLancarExceçãoAoTentarSacarComSaldoInsuficiente() {

		Integer id = 1;
		BigDecimal valor = new BigDecimal(4000);

		when(contaRepository.findById(id)).thenReturn(Optional.of(conta));

		assertThrows(SaldoInsuficienteException.class, () -> contaService.sacar(valor, id));

		verify(contaRepository, never()).save(any(Conta.class));
	}

	@Test
	@DisplayName("Deve realizar a transferencia com sucesso")
	void deveRealizarTransferenciaComSucesso() {

		Integer contaIdDepositar = 1;
		Integer contaIdReceber = 2;
		BigDecimal valorTransferencia = new BigDecimal(200);

		Conta contaReceber = new ContaPoupanca();
		contaReceber.setId(contaIdReceber);
		contaReceber.setSaldo(new BigDecimal(1000));

		when(contaRepository.findById(contaIdDepositar)).thenReturn(Optional.of(conta));
		when(contaRepository.findById(contaIdReceber)).thenReturn(Optional.of(contaReceber));

		List<Conta> contasNovas = contaService.transferir(contaIdDepositar, valorTransferencia, contaIdReceber);
		Conta contaSaidaAtualizada = contasNovas.stream().filter(c -> c.getId().equals(contaIdDepositar)).findFirst()
				.get();
		Conta contaEntradaAtualizada = contasNovas.stream().filter(c -> c.getId().equals(contaIdReceber)).findFirst()
				.get();

		assertEquals(0, new BigDecimal("800.00").compareTo(contaSaidaAtualizada.getSaldo()));
		assertEquals(0, new BigDecimal("1200.00").compareTo(contaEntradaAtualizada.getSaldo()));

		verify(contaRepository).save(contaReceber);
		verify(contaRepository).save(conta);
	}

	@Test
	@DisplayName("Deve retornar exceção de id de conta que ira depositar inválido")
	void deveLancarExcecaoQuandoContaDeSaidaNaoExiste() {

		Integer contaIdDepositar = 1;
		Integer contaIdReceber = 2;
		BigDecimal valorTransferencia = new BigDecimal(200);

		Conta contaReceber = new ContaPoupanca();
		contaReceber.setId(contaIdReceber);
		contaReceber.setSaldo(new BigDecimal(1000));

		when(contaRepository.findById(contaIdDepositar)).thenReturn(Optional.empty());

		assertThrows(ContaInexistenteException.class,
				() -> contaService.transferir(contaIdDepositar, valorTransferencia, contaIdReceber));

		verify(contaRepository, never()).save(any(Conta.class));

	}

	@Test
	@DisplayName("Deve retornar exceção de id de conta que ira receber inválido")
	void deveLancarExcecaoQuandoContaDeEntradaNaoExiste() {

		Integer contaIdDepositar = 1;
		Integer contaIdReceber = 2;
		BigDecimal valorTransferencia = new BigDecimal(200);

		Conta contaReceber = new ContaPoupanca();
		contaReceber.setId(contaIdReceber);
		contaReceber.setSaldo(new BigDecimal(1000));

		when(contaRepository.findById(contaIdDepositar)).thenReturn(Optional.of(conta));

		when(contaRepository.findById(contaIdReceber)).thenReturn(Optional.empty());

		assertThrows(ContaInexistenteException.class,
				() -> contaService.transferir(contaIdDepositar, valorTransferencia, contaIdReceber));

		verify(contaRepository, never()).save(any(Conta.class));

	}

	@Test
	@DisplayName("Deve retornar uma exceção de saldo insuficiente para transferencia")
	void deveLancarExcecaoDeSaldoInsuficienteParaTransferencia() {

		Integer contaIdDepositar = 1;
		Integer contaIdReceber = 2;
		BigDecimal valorTransferencia = new BigDecimal(599999);

		Conta contaReceber = new ContaPoupanca();
		contaReceber.setId(contaIdReceber);
		contaReceber.setSaldo(new BigDecimal(1000));

		when(contaRepository.findById(contaIdDepositar)).thenReturn(Optional.of(conta));

		when(contaRepository.findById(contaIdReceber)).thenReturn(Optional.of(contaReceber));

		assertThrows(SaldoInsuficienteException.class,
				() -> contaService.transferir(contaIdDepositar, valorTransferencia, contaIdReceber));

		verify(contaRepository, never()).save(any(Conta.class));

	}

	@Test
	@DisplayName("Deve retornar uma exceção de valor menor do que 0 para transferencia")
	void deveLancarExcecaoDeValorMenorQueZeroParaTransferencia() {

		Integer contaIdDepositar = 1;
		Integer contaIdReceber = 2;
		BigDecimal valorTransferencia = new BigDecimal(-1);

		Conta contaReceber = new ContaPoupanca();
		contaReceber.setId(contaIdReceber);
		contaReceber.setSaldo(new BigDecimal(1000));

		when(contaRepository.findById(contaIdDepositar)).thenReturn(Optional.of(conta));

		when(contaRepository.findById(contaIdReceber)).thenReturn(Optional.of(contaReceber));

		assertThrows(ValorMenorQueZeroException.class,
				() -> contaService.transferir(contaIdDepositar, valorTransferencia, contaIdReceber));

		verify(contaRepository, never()).save(any(Conta.class));

	}

	@Test
	@DisplayName("Deve criar com sucesso uma conta CC")
	void deveCriarUmaContaCCComSucesso() {
		Integer clienteId = 1;

		Cliente clienteMock = new Cliente("Wallace", "000.111.222-88");
		clienteMock.setId(clienteId);

		ContaRequestDTO contaRequestDTO = new ContaRequestDTO(clienteId, "CC");

		when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));

		contaService.criarConta(contaRequestDTO);

		ArgumentCaptor<Conta> contaArgumentCaptor = ArgumentCaptor.forClass(Conta.class);

		verify(contaRepository).save(contaArgumentCaptor.capture());

		Conta contaSalva = contaArgumentCaptor.getValue();

		assertNotNull(contaSalva);
		assertTrue(contaSalva instanceof ContaCorrente);
		assertEquals(clienteMock, contaSalva.getCliente());
		assertEquals(0, BigDecimal.ZERO.compareTo(contaSalva.getSaldo()));
	}

	@Test
	@DisplayName("Deve criar com sucesso uma conta CP")
	void deveCriarUmaContaCPComSucesso() {
		Integer clienteId = 1;

		Cliente clienteMock = new Cliente("Wallace", "000.111.222-88");
		clienteMock.setId(clienteId);

		ContaRequestDTO contaRequestDTO = new ContaRequestDTO(clienteId, "CP");

		when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(clienteMock));

		contaService.criarConta(contaRequestDTO);

		ArgumentCaptor<Conta> contaArgumentCaptor = ArgumentCaptor.forClass(Conta.class);

		verify(contaRepository).save(contaArgumentCaptor.capture());

		Conta contaSalva = contaArgumentCaptor.getValue();

		assertNotNull(contaSalva);
		assertTrue(contaSalva instanceof ContaPoupanca);
		assertEquals(clienteMock, contaSalva.getCliente());
		assertEquals(0, BigDecimal.ZERO.compareTo(contaSalva.getSaldo()));
	}

	@Test
	@DisplayName("Deve lançar uma exceção de cliente inexistente")
	void develancarexceçãodeclienteinexistente() {
		Integer clienteId = 1;

		Cliente clienteMock = new Cliente("Wallace", "000.111.222-88");
		clienteMock.setId(clienteId);

		ContaRequestDTO contaRequestDTO = new ContaRequestDTO(clienteId, "CP");

		when(clienteRepository.findById(contaRequestDTO.clienteId())).thenReturn(Optional.empty());

		assertThrows(ClienteNaoEncontradoException.class, () -> {
			contaService.criarConta(contaRequestDTO);
		});

		verify(contaRepository, never()).save(any(Conta.class));

	}
	
	@Test
	@DisplayName("Deve lançar uma exceção de tipo de conta invalida")
	void develancarexceçãodetipodecontainvalida() {
		Integer clienteId = 1;

		Cliente clienteMock = new Cliente("Wallace", "000.111.222-88");
		clienteMock.setId(clienteId);

		ContaRequestDTO contaRequestDTO = new ContaRequestDTO(clienteId, "XX");

		when(clienteRepository.findById(contaRequestDTO.clienteId())).thenReturn(Optional.of(clienteMock));

		assertThrows(TipoDeContaInvalidaException.class, () -> {
			contaService.criarConta(contaRequestDTO);
		});

		verify(contaRepository, never()).save(any(Conta.class));

	}
	
	@Test
	@DisplayName("Deve lançar uma exceção de conta CC ja existente")
	void develancarexceçãodecontaCCjaexistente() {
		Integer clienteId = 1;

		Cliente clienteMock = new Cliente("Wallace", "000.111.222-88");
		clienteMock.setId(clienteId);
		
		clienteMock.adicionarNovaConta(new ContaCorrente());

		ContaRequestDTO contaRequestDTO = new ContaRequestDTO(clienteId, "CC");

		when(clienteRepository.findById(contaRequestDTO.clienteId())).thenReturn(Optional.of(clienteMock));

		assertThrows(ContaJaExistenteException.class, () -> {
			contaService.criarConta(contaRequestDTO);
		});

		verify(contaRepository, never()).save(any(Conta.class));

	}
	
	@Test
	@DisplayName("Deve lançar uma exceção de conta CP ja existente")
	void develancarexceçãodecontaCPjaexistente() {
		Integer clienteId = 1;
		
		Cliente clienteMock = new Cliente("Wallace", "000.111.222-88");
		clienteMock.setId(clienteId);
		clienteMock.adicionarNovaConta(new ContaPoupanca());
		
		ContaRequestDTO contaRequestDTO = new ContaRequestDTO(clienteId, "CP");
		
		when(clienteRepository.findById(contaRequestDTO.clienteId())).thenReturn(Optional.of(clienteMock));
		
		assertThrows(ContaJaExistenteException.class, () -> {
			contaService.criarConta(contaRequestDTO);
		});
		
		verify(contaRepository, never()).save(any(Conta.class));
	}
	
	@Test
    @DisplayName("Deve simular o rendimento com sucesso")
    void deveSimularRendimentoComSucesso() { 
    	
    	Integer id = 1;
    	LocalDate dataPrevista = LocalDate.now().plusMonths(2);
    	
    	ContaPoupanca contaPoupanca = new ContaPoupanca();
    	contaPoupanca.setId(id);
    	contaPoupanca.setSaldo(new BigDecimal("1000.00"));

    	when(contaRepository.findById(id)).thenReturn(Optional.of(contaPoupanca));
    	
    	BigDecimal valorEsperado = contaPoupanca.getSaldo().multiply(TAXA_PARA_TESTE).multiply(new BigDecimal(2));

    	BigDecimal valorSimulado = contaService.simularRendimento(dataPrevista, id);
    	
    	assertNotNull(valorSimulado);
    	assertEquals(0, valorEsperado.compareTo(valorSimulado));
    	
    	verify(contaRepository).findById(id);
    }

	

	@Test
    @DisplayName("Deve lancar uma excecao de data antes do dia atual")
    void deveLancarExcecaoQuandoDataForAnteriorAAtual() {
    	
    	Integer id = 1;
    	LocalDate dataPrevista = LocalDate.now().minusMonths(2);

    	assertThrows(DataInvalidaException.class,() -> contaService.simularRendimento(dataPrevista, id));

    	verify(contaRepository, never()).findById(anyInt());
	 }
	
	@Test
	@DisplayName("Deve lancar uma excecao de data menor que 1 mes")
	void deveLancarExcecaoQuandoDataForMenorA1Mes() {
		
		Integer id = 1;
		LocalDate dataPrevista = LocalDate.now().plusDays(20);
		
		assertThrows(DataInvalidaException.class,() -> contaService.simularRendimento(dataPrevista, id));
		
		verify(contaRepository, never()).findById(anyInt());
	}
	
	@Test
	@DisplayName("Deve lançar uma exceção quando for inserido uma conta não poupança")
	void deveLancarUmaExcecaoQuandoForInseridoUmaContaNaoPoupanca() {
		
		Integer id = 1;
    	LocalDate dataPrevista = LocalDate.now().plusMonths(2);
    	when(contaRepository.findById(id)).thenReturn(Optional.of(conta)); 
    	
		assertThrows(TipoDeContaInvalidaException.class,() -> contaService.simularRendimento(dataPrevista, id));

		verify(contaRepository, never()).save(any(Conta.class));
	}
	@Test
	@DisplayName("Deve lançar uma exceção quando a conta não existir")
	void deveLancarUmaExcecaoQuandoAContaNaoExistir() {
		
		Integer id = 1;
    	LocalDate dataPrevista = LocalDate.now().plusMonths(2);
    	when(contaRepository.findById(id)).thenReturn(Optional.empty());
    	
		assertThrows(ContaInexistenteException.class,() -> contaService.simularRendimento(dataPrevista, id));
		
		verify(contaRepository, never()).save(any(Conta.class));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}