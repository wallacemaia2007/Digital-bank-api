package com.wallace.spring.boot.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Entity;

@Entity
public class ContaPoupanca extends Conta {

	public BigDecimal simularRendimento(BigDecimal taxaJurosMensal, long meses) {
		if (meses <= 0) {
			return this.getSaldo();
		}

		BigDecimal saldoSimulado = this.getSaldo();

		for (int i = 0; i < meses; i++) {
			BigDecimal rendimentoDoMes = saldoSimulado.multiply(taxaJurosMensal);
			saldoSimulado = saldoSimulado.add(rendimentoDoMes);
		}

		return saldoSimulado.setScale(2, RoundingMode.HALF_UP);
	}
}