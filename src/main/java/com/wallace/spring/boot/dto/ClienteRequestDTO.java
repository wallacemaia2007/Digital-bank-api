package com.wallace.spring.boot.dto;

import org.hibernate.validator.constraints.br.CPF;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para cadastrar ou atualizar um cliente")
public record ClienteRequestDTO(
        @Schema(description = "Nome completo do cliente", example = "Wallace Santos")
        @NotBlank(message = "O nome não pode estar em branco")
        String nome,

        @Schema(description = "CPF do cliente (formato: xxx.xxx.xxx-xx)", example = "123.456.789-00")
        @NotBlank(message = "O CPF não pode estar em branco")
        @CPF(message = "O formato do CPF é inválido")
        String cpf
) {}