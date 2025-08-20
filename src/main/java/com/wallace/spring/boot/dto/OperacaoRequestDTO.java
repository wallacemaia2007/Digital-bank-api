package com.wallace.spring.boot.dto;

import java.math.BigDecimal;

public class OperacaoRequestDTO {
	
	private BigDecimal valor;
	private Integer contaId;
	
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Integer getContaId() {
		return contaId;
	}
	public void setContaId(Integer contaId) {
		this.contaId = contaId;
	}
	
	public OperacaoRequestDTO() {
		super();
	}

	
}
