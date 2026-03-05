package com.wallace.spring.boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Estrutura padrão de resposta para erros da API")
public record ErroResponse(
    @Schema(description = "Data e hora em que o erro ocorreu", example = "2025-09-16T16:08:18.123456")
    LocalDateTime timestamp,

    @Schema(description = "Mensagem resumida do erro", example = "Cliente não encontrado")
    String message,
    
    @Schema(description = "Detalhes técnicos do erro, como a URI da requisição", example = "uri=/clientes/999")
    String details
) {}