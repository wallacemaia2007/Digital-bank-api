package com.wallace.spring.boot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallace.spring.boot.model.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Integer>  {

}
