package com.wallace.spring.boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Schema(description = "Dados para uma operação de saque ou depósito")
public record OperacaoRequestDTO(
        @Schema(description = "Valor da operação (saque ou depósito). Deve ser maior que zero.", example = "200.00")
        @NotNull(message = "O valor da operação é obrigatório")
        @Positive(message = "O valor da operação deve ser maior que zero")
        BigDecimal valor,

        @Schema(description = "ID da conta onde a operação será realizada", example = "1")
        @NotNull(message = "O ID da conta é obrigatório")
        Integer contaId
) {}