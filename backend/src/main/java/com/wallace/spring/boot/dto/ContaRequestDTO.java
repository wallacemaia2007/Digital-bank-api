package com.wallace.spring.boot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Dados para criar uma nova conta bancária")
public record ContaRequestDTO(
        @Schema(description = "ID do cliente ao qual a conta será associada", example = "1")
        @NotNull(message = "O ID do cliente é obrigatório")
        Integer clienteId,

        @Schema(description = "Tipo da conta. Use 'CC' para Conta Corrente ou 'CP' para Conta Poupança.", example = "CC")
        @NotBlank(message = "O tipo da conta é obrigatório")
        @Pattern(regexp = "CC|CP", message = "Tipo de conta inválido. Use 'CC' ou 'CP'.")
        String tipoConta
) {}