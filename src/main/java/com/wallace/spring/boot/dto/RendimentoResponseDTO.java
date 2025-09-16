package com.wallace.spring.boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Dados de resposta para a simulação de rendimento")
public record RendimentoResponseDTO(
    @Schema(description = "Valor total simulado após o rendimento", example = "1050.25")
    BigDecimal valor
) {}