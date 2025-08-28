package com.wallace.spring.boot.model.entities;

import java.math.BigDecimal;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CP")
public class ContaPoupanca extends Conta {

	public BigDecimal simularRendimento(BigDecimal taxa, long meses) {
		BigDecimal numeroDeMeses = new BigDecimal(meses);
		return this.getSaldo().multiply(taxa).multiply(numeroDeMeses);
	}
}