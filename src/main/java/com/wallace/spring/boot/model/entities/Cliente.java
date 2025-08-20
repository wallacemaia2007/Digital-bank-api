package com.wallace.spring.boot.model.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.hibernate.validator.constraints.br.CPF;

import com.wallace.spring.boot.exceptions.ContaJaExistenteException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id")
	private Integer id;
	
	@Size(max = 100, min = 3, message = "O nome deve conter no mínimo 3 caracteres ne máximo 100")
	@Column(name = "Nome", nullable = false)
	private String nome;
	
	@CPF(message = "Cpf inválido")
	@Column(name = "Cpf")
	private String cpf;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Set<Conta> contas = new HashSet<Conta>();

	public void adicionarNovaConta(Conta novaConta) {
		for(Conta contasDoCliente : this.contas) {
			if(contasDoCliente.getClass().equals(novaConta.getClass())) {
				throw new ContaJaExistenteException("Só pode haver uma conta corrente por cliente!");
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