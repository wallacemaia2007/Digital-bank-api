package com.wallace.spring.boot.dto;

import java.math.BigDecimal;

public record TransferenciaRequestDTO(Integer contaIdEntrada, Integer contaIdSaida, BigDecimal valor) {

}
