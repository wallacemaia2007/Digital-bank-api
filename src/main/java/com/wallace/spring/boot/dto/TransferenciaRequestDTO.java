package com.wallace.spring.boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransferenciaRequestDTO(
        @Schema(description = "ID da conta que irá enviar o dinheiro (remetente)", example = "1")
        @NotNull(message = "O ID da conta remetente é obrigatório")
        Integer contaIdDepositar,

        @Schema(description = "ID da conta que irá receber o dinheiro (destinatário)", example = "2")
        @NotNull(message = "O ID da conta destinatária é obrigatório")
        Integer contaIdReceber,

        @Schema(description = "Valor a ser transferido. Deve ser maior que zero.", example = "150.75")
        @NotNull(message = "O valor da transferência é obrigatório")
        @Positive(message = "O valor da transferência deve ser maior que zero")
        BigDecimal valor
) {}