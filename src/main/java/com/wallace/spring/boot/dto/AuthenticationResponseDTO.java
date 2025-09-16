package com.wallace.spring.boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da API contendo o token JWT para autenticação")
public record AuthenticationResponseDTO(
    @Schema(description = "Token JWT gerado após o login bem-sucedido.", 
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YWxsYWNlLnNhbnRvc0BleGFtcGxlLmNvbSIsImlhdCI6MTY3OTg1NjAwMCwiZXhwIjoxNjc5OTQyNDAwfQ....")
    String token
) {}