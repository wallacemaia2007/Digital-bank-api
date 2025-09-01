package com.wallace.spring.boot.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import com.wallace.spring.boot.model.entities.HistoricoConta;

public record HistoricoContaResponseDTO(
        Integer id,
        String tipoDaTransacao,
        BigDecimal valor,
        String horario,
        Integer idRemetente,
        String nomeRemetente,
        Integer idRecebedor,
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
