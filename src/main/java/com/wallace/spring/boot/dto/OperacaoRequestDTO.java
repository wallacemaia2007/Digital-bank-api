package com.wallace.spring.boot.dto;

import java.math.BigDecimal;

public record OperacaoRequestDTO(BigDecimal valor, Integer contaId) {

}
