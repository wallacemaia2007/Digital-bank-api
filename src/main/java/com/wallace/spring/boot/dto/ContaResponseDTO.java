package com.wallace.spring.boot.dto;

import com.wallace.spring.boot.model.entities.Conta;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Dados de resposta para uma conta bancária")
public record ContaResponseDTO(
    @Schema(description = "ID único da conta", example = "1")
    Integer id,
    @Schema(description = "Tipo da conta (ContaCorrente ou ContaPoupanca)", example = "ContaCorrente")
    String tipoConta,
    @Schema(description = "Saldo atual da conta", example = "1250.50")
    BigDecimal saldo,
    @Schema(description = "Dados do cliente titular da conta")
    ClienteResponseDTO cliente
) {
    public ContaResponseDTO(Conta conta) {
        this(conta.getId(), conta.getClass().getSimpleName(), conta.getSaldo(), new ClienteResponseDTO(conta.getCliente()));
    }
}