package com.wallace.spring.boot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wallace.spring.boot.model.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>  {

}
