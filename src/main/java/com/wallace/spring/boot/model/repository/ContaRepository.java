package com.wallace.spring.boot.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallace.spring.boot.model.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Integer> {

	Optional<Conta> findByCpf(String cpf);

}
