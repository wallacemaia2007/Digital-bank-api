package com.wallace.spring.boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta da API contendo o token JWT para autenticação")
public record AuthenticationResponseDTO(
    @Schema(description = "Token JWT gerado após o login bem-sucedido.", 
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YWxsYWNlLm1haWFAZXhhbXBsZS5jb20iLCJpYXQiOjE2Nzk4NTYwMDAsImV4cCI6MTY3OTk0MjQwMH0....")
    String token
) {}