package com.wallace.spring.boot.dto;

import java.math.BigDecimal;

public class TransferenciaRequestDTO {
	
	private Integer contaIdEntrada;
	private Integer contaIdSaida;
	private BigDecimal valor;
	
	public TransferenciaRequestDTO() {
		super();
	}
	
	
	public Integer getContaIdEntrada() {
		return contaIdEntrada;
	}
	public void setContaIdEntrada(Integer contaIdEntrada) {
		this.contaIdEntrada = contaIdEntrada;
	}
	public Integer getContaIdSaida() {
		return contaIdSaida;
	}
	public void setContaIdSaida(Integer contaIdSaida) {
		this.contaIdSaida = contaIdSaida;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	
	

}
