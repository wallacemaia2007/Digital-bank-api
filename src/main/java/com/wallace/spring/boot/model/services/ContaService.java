package com.wallace.spring.boot.model.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.exceptions.ContaInexistenteException;
import com.wallace.spring.boot.exceptions.DataInvalidaException;
import com.wallace.spring.boot.exceptions.SaldoInsuficienteException;
import com.wallace.spring.boot.exceptions.ValorMenorQueZeroException;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.entities.Conta;
import com.wallace.spring.boot.model.entities.ContaPoupanca;
import com.wallace.spring.boot.model.repository.ClienteRepository;
import com.wallace.spring.boot.model.repository.ContaRepository;

import jakarta.transaction.Transactional;

@Service
public class ContaService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ContaRepository contaRepository;

	@Transactional
	public boolean depositar(BigDecimal valor, Conta conta) {

		if (valor.signum() <= 0)
			throw new ValorMenorQueZeroException("O valor para depósito deve ser maior do que 0 ");
		conta.setSaldo(conta.getSaldo().add(valor));

		contaRepository.save(conta);

		return true;

	}

	@Transactional
	public boolean sacar(BigDecimal valor, Conta conta) {
		if (valor.signum() <= 0) {
			throw new ValorMenorQueZeroException("O valor do saque deve ser maior que zero.");
		}
		if (conta.getSaldo().compareTo(valor) < 0) {
			throw new SaldoInsuficienteException("Saldo insuficiente.");
		}

		conta.setSaldo(conta.getSaldo().subtract(valor));

		contaRepository.save(conta);

		return true;
	}

	@Transactional
	public boolean transferir(Conta conta, BigDecimal valor, Conta contaFinal) {
		if (valor.signum() <= 0)
			throw new ValorMenorQueZeroException("O valor para realizar a transferência deve ser maior do que 0");
		if (conta.getSaldo().compareTo(valor) < 0)
			throw new SaldoInsuficienteException("Saldo insuficiente na conta para realizar a transferência");

		conta.setSaldo(conta.getSaldo().subtract(valor));
		contaFinal.setSaldo(contaFinal.getSaldo().add(valor));

		contaRepository.save(conta);
		contaRepository.save(contaFinal);

		return true;
	}

	public BigDecimal simularRendimento(LocalDate dataPrevista, String cpf) {

		LocalDate dataAtual = LocalDate.now();
		long meses = ChronoUnit.MONTHS.between(dataAtual, dataPrevista);
		if (dataPrevista.isBefore(dataAtual))
			throw new DataInvalidaException("Para calcular o rendimento deve-se digitar uma data após o dia de hoje!");

		if (meses < 1)
			throw new DataInvalidaException(
					"Para calcular o rendimento deve-se digitar uma data acima de 1 mês da data atual!");

		Cliente cliente = clienteRepository.findByCpf(cpf)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado para o CPF: " + cpf));

		ContaPoupanca contaPoupanca = cliente.getContaPoupanca();
		if (contaPoupanca == null)
			throw new ContaInexistenteException("Este cliente não possui conta poupança");

		BigDecimal valorSimulado = contaPoupanca.simularRendimento(new BigDecimal(0.89), meses);
		System.out.printf("O Rendimento por %d meses será de $%f reais%n", meses, valorSimulado);

		return valorSimulado;
	}

}
