package com.wallace.spring.boot.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wallace.spring.boot.enums.TipoTransacao;
import com.wallace.spring.boot.model.entities.Conta;
import com.wallace.spring.boot.model.entities.ContaCorrente;
import com.wallace.spring.boot.model.entities.HistoricoConta;
import com.wallace.spring.boot.model.repository.HistoricoContaRepository;
import com.wallace.spring.boot.model.services.HistoricoContaService;

@ExtendWith(MockitoExtension.class)
public class HistoricoContaServiceTest {

	@Mock
	private HistoricoContaRepository historicoContaRepository;

	private HistoricoContaService historicoContaService;

	@BeforeEach
	void setUp() {
		historicoContaService = new HistoricoContaService(historicoContaRepository);
	}

	@Test
	void deveRegistrarDeposito() {
		Conta conta = new ContaCorrente();
		BigDecimal valor = BigDecimal.TEN;
		HistoricoConta esperado = new HistoricoConta(TipoTransacao.DEPOSITO, valor, conta, null);

		when(historicoContaRepository.save(any(HistoricoConta.class))).thenReturn(esperado);

		HistoricoConta resultado = historicoContaService.registrarDeposito(conta, valor);

		assertEquals(TipoTransacao.DEPOSITO, resultado.getTipoDaTransacao());
		assertEquals(valor, resultado.getValor());
		assertEquals(conta, resultado.getEfetuouTransacao());
		assertNull(resultado.getRecebeuTransacao());
	}

	@Test
	void deveRegistrarSaque() {
		Conta conta = new ContaCorrente();
		BigDecimal valor = BigDecimal.TEN;
		HistoricoConta esperado = new HistoricoConta(TipoTransacao.SAQUE, valor, conta, null);

		when(historicoContaRepository.save(any(HistoricoConta.class))).thenReturn(esperado);

		HistoricoConta resultado = historicoContaService.registrarSaque(conta, valor);

		assertEquals(TipoTransacao.SAQUE, resultado.getTipoDaTransacao());
		assertEquals(valor, resultado.getValor());
		assertEquals(conta, resultado.getEfetuouTransacao());
		assertNull(resultado.getRecebeuTransacao());
	}

	@Test
	void deveRegistrarTransferencia() {
		Conta contaRemetente = new ContaCorrente();
		Conta contaDestino = new ContaCorrente();
		BigDecimal valor = BigDecimal.TEN;
		HistoricoConta esperado = new HistoricoConta(TipoTransacao.TRANSFERENCIA, valor, contaRemetente, contaDestino);

		when(historicoContaRepository.save(any(HistoricoConta.class))).thenReturn(esperado);

		HistoricoConta resultado = historicoContaService.registrarTransferencia(contaRemetente, valor, contaDestino);

		assertEquals(TipoTransacao.TRANSFERENCIA, resultado.getTipoDaTransacao());
		assertEquals(valor, resultado.getValor());
		assertEquals(contaRemetente, resultado.getEfetuouTransacao());
		assertEquals(contaDestino, resultado.getRecebeuTransacao());
	}

}
