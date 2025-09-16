package com.wallace.spring.boot.dto;

import com.wallace.spring.boot.model.entities.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta para um cliente")
public record ClienteResponseDTO(
    @Schema(description = "ID único do cliente", example = "1")
    Integer id,
    @Schema(description = "Nome completo do cliente", example = "Wallace Santos")
    String nome,
    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    String cpf
) {
    public ClienteResponseDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getCpf());
    }
}