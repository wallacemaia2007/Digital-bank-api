package com.wallace.spring.boot.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import com.wallace.spring.boot.model.entities.HistoricoConta;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhes de uma única transação no histórico da conta")
public record HistoricoContaResponseDTO(
        @Schema(description = "ID da transação", example = "101")
        Integer id,
        @Schema(description = "Tipo da transação (DEPOSITO, SAQUE, TRANSFERENCIA)", example = "TRANSFERENCIA")
        String tipoDaTransacao,
        @Schema(description = "Valor da transação", example = "100.00")
        BigDecimal valor,
        @Schema(description = "Data e hora da transação", example = "16/09/2025 17:30:00")
        String horario,
        @Schema(description = "ID da conta remetente (se aplicável)", nullable = true, example = "1")
        Integer idRemetente,
        @Schema(description = "Nome do cliente remetente (se aplicável)", nullable = true, example = "Wallace Santos")
        String nomeRemetente,
        @Schema(description = "ID da conta destinatária (se aplicável)", nullable = true, example = "2")
        Integer idRecebedor,
        @Schema(description = "Nome do cliente destinatário (se aplicável)", nullable = true, example = "Maria Silva")
        String nomeRecebedor
) {
    public HistoricoContaResponseDTO(HistoricoConta entity) {
        this(
            entity.getId(),
            entity.getTipoDaTransacao().name(),
            entity.getValor(),
            entity.getHorarioTransacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
            entity.getEfetuouTransacao() != null ? entity.getEfetuouTransacao().getId() : null,
            entity.getEfetuouTransacao() != null ? entity.getEfetuouTransacao().getCliente().getNome() : null,
            entity.getRecebeuTransacao() != null ? entity.getRecebeuTransacao().getId() : null,
            entity.getRecebeuTransacao() != null ? entity.getRecebeuTransacao().getCliente().getNome() : null
        );
    }
}