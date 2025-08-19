package com.wallace.spring.boot.model.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.wallace.spring.boot.exceptions.DomainException;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

	private Integer id;
	private String nome;
	private String cpf;

	private Set<Conta> contas = new HashSet<Conta>();

	public void adicionarNovaConta(Conta novaConta) {
		for(Conta contasDoCliente : this.contas) {
			if(contasDoCliente.getClass().equals(novaConta.getClass())) {
				throw new DomainException("Só pode haver uma conta corrente por cliente!");
			}
		}
		this.contas.add(novaConta);
	}
	
	public Cliente() {
		super();
	}

	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public ContaCorrente getContaCorrente() {
		for (Conta conta : contas) {
			if (conta instanceof ContaCorrente) {
				return (ContaCorrente) conta;
			}
		}
		return null;
	}

	public ContaPoupanca getContaPoupanca() {
		for (Conta conta : contas) {
			if (conta instanceof ContaPoupanca) {
				return (ContaPoupanca) conta;
			}
		}
		return null;
	}


	public Set<Conta> getContas() {
		return contas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", contas=" + contas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}
	

}