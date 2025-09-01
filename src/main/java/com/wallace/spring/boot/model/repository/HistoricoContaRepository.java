package com.wallace.spring.boot.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallace.spring.boot.model.entities.HistoricoConta;

public interface HistoricoContaRepository extends JpaRepository<HistoricoConta,Integer> {
	
	List<HistoricoConta> findByEfetuouTransacaoIdOrRecebeuTransacaoId(Integer id1, Integer id2);

}
