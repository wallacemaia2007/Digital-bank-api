package com.wallace.spring.boot.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.wallace.spring.boot.enums.TipoTransacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historico_conta")
public class HistoricoConta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_transacao")
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_transacao", nullable = false)
	private TipoTransacao tipoDaTransacao;

	@Column(name = "valor", nullable = false)
	private BigDecimal valor;

	@Column(name = "horario", nullable = false)
	private LocalDateTime horarioTransacao;

	@ManyToOne
	@JoinColumn(name = "id_remetente", nullable = false)
	private Conta efetuouTransacao;

	@ManyToOne
	@JoinColumn(name = "id_recebedor", nullable = true)
	private Conta recebeuTransacao;

	public HistoricoConta() {
	}

	public HistoricoConta(TipoTransacao tipoDaTransacao, BigDecimal valor, Conta efetuouTransacao,
			Conta recebeuTransacao) {
		this.tipoDaTransacao = tipoDaTransacao;
		this.valor = valor;
		this.efetuouTransacao = efetuouTransacao;
		this.recebeuTransacao = recebeuTransacao;
		this.horarioTransacao = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipoTransacao getTipoDaTransacao() {
		return tipoDaTransacao;
	}

	public void setTipoDaTransacao(TipoTransacao tipoDaTransacao) {
		this.tipoDaTransacao = tipoDaTransacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDateTime getHorarioTransacao() {
		return horarioTransacao;
	}

	public void setHorarioTransacao(LocalDateTime horarioTransacao) {
		this.horarioTransacao = horarioTransacao;
	}

	public Conta getEfetuouTransacao() {
		return efetuouTransacao;
	}

	public void setEfetuouTransacao(Conta efetuouTransacao) {
		this.efetuouTransacao = efetuouTransacao;
	}

	public Conta getRecebeuTransacao() {
		return recebeuTransacao;
	}

	public void setRecebeuTransacao(Conta recebeuTransacao) {
		this.recebeuTransacao = recebeuTransacao;
	}

}
