package com.wallace.spring.boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados necessários para a autenticação de um usuário")
public record AuthenticationRequestDTO(
        @Schema(description = "E-mail do usuário", example = "wallace.santos@example.com")
        @NotBlank(message = "O e-mail não pode estar em branco")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @Schema(description = "Senha do usuário", example = "StrongPwd123")
        @NotBlank(message = "A senha не pode estar em branco")
        String senha
) {}