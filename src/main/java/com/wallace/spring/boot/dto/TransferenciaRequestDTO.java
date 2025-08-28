package com.wallace.spring.boot.dto;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(Integer contaIdDepositar, Integer contaIdReceber, BigDecimal valor) {

}
