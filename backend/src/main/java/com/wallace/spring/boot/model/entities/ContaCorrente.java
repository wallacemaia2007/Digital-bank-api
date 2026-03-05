package com.wallace.spring.boot.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CC")
public class ContaCorrente extends Conta {
	
}