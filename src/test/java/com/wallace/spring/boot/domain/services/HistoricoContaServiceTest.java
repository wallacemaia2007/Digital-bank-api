package com.wallace.spring.boot.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

		historicoContaService.registrarDeposito(conta, valor);

		ArgumentCaptor<HistoricoConta> historicoCaptor = ArgumentCaptor.forClass(HistoricoConta.class);

		verify(historicoContaRepository).save(historicoCaptor.capture());

		HistoricoConta resultadoCapturado = historicoCaptor.getValue();

		assertEquals(TipoTransacao.DEPOSITO, resultadoCapturado.getTipoDaTransacao());
		assertEquals(valor, resultadoCapturado.getValor());
		assertEquals(conta, resultadoCapturado.getEfetuouTransacao());
		assertNull(resultadoCapturado.getRecebeuTransacao());
	}

	@Test
	void deveRegistrarSaque() {
		Conta conta = new ContaCorrente();
		BigDecimal valor = BigDecimal.TEN;

		historicoContaService.registrarSaque(conta, valor);

		ArgumentCaptor<HistoricoConta> historicoCaptor = ArgumentCaptor.forClass(HistoricoConta.class);

		verify(historicoContaRepository).save(historicoCaptor.capture());

		HistoricoConta resultadoCapturado = historicoCaptor.getValue();

		assertEquals(TipoTransacao.SAQUE, resultadoCapturado.getTipoDaTransacao());
		assertEquals(valor, resultadoCapturado.getValor());
		assertEquals(conta, resultadoCapturado.getEfetuouTransacao());
		assertNull(resultadoCapturado.getRecebeuTransacao());
	}

	@Test
	void deveRegistrarTransferenciaComCaptor() { 
		Conta contaRemetente = new ContaCorrente();
		Conta contaDestino = new ContaCorrente();
		BigDecimal valor = BigDecimal.TEN;

		historicoContaService.registrarTransferencia(contaRemetente, valor, contaDestino);

		ArgumentCaptor<HistoricoConta> historicoCaptor = ArgumentCaptor.forClass(HistoricoConta.class);

		verify(historicoContaRepository).save(historicoCaptor.capture());

		HistoricoConta resultadoCapturado = historicoCaptor.getValue();

		assertEquals(TipoTransacao.TRANSFERENCIA, resultadoCapturado.getTipoDaTransacao());
		assertEquals(valor, resultadoCapturado.getValor());
		assertEquals(contaRemetente, resultadoCapturado.getEfetuouTransacao());
		assertEquals(contaDestino, resultadoCapturado.getRecebeuTransacao());
	}
}
