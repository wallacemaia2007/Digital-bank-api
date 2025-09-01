package com.wallace.spring.boot.model.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wallace.spring.boot.enums.TipoTransacao;
import com.wallace.spring.boot.model.entities.Conta;
import com.wallace.spring.boot.model.entities.HistoricoConta;
import com.wallace.spring.boot.model.repository.HistoricoContaRepository;

@Service
public class HistoricoContaService {

	private final HistoricoContaRepository historicoContaRepository;

	public HistoricoContaService(HistoricoContaRepository historicoContaRepository) {
		this.historicoContaRepository = historicoContaRepository;
	}

	public HistoricoConta registrarSaque(Conta conta, BigDecimal valor) {
		HistoricoConta historicoConta = new HistoricoConta(TipoTransacao.SAQUE, valor, conta, null);
		return historicoContaRepository.save(historicoConta);
	}

	public HistoricoConta registrarDeposito(Conta conta, BigDecimal valor) {
		HistoricoConta historicoConta = new HistoricoConta(TipoTransacao.DEPOSITO, valor, conta, null);
		return historicoContaRepository.save(historicoConta);
	}

	public HistoricoConta registrarTransferencia(Conta contaRemetente, BigDecimal valor, Conta conta) {
		HistoricoConta historicoConta = new HistoricoConta(TipoTransacao.TRANSFERENCIA, valor, contaRemetente, conta);
		return historicoContaRepository.save(historicoConta);
	}
	
	public List<HistoricoConta> mostrarRegistrosTransacoes(Integer id){
		return historicoContaRepository.findByEfetuouTransacaoIdOrRecebeuTransacaoId(id,id);
	}

}
