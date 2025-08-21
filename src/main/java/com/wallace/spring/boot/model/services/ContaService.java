package com.wallace.spring.boot.model.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallace.spring.boot.dto.ContaRequestDTO;
import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.exceptions.ContaInexistenteException;
import com.wallace.spring.boot.exceptions.DataInvalidaException;
import com.wallace.spring.boot.exceptions.SaldoInsuficienteException;
import com.wallace.spring.boot.exceptions.TipoDeContaInvalidaException;
import com.wallace.spring.boot.exceptions.ValorMenorQueZeroException;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.entities.Conta;
import com.wallace.spring.boot.model.entities.ContaCorrente;
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
	public Conta depositar(BigDecimal valor, Integer id) {

		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));
		if (valor.signum() <= 0)
			throw new ValorMenorQueZeroException("O valor para depósito deve ser maior do que 0 ");
		conta.setSaldo(conta.getSaldo().add(valor));

		return contaRepository.save(conta);

	}

	@Transactional
	public Conta sacar(BigDecimal valor, Integer id) {
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));
		if (valor.signum() <= 0) {
			throw new ValorMenorQueZeroException("O valor do saque deve ser maior que zero.");
		}
		if (conta.getSaldo().compareTo(valor) < 0) {
			throw new SaldoInsuficienteException("Saldo insuficiente.");
		}

		conta.setSaldo(conta.getSaldo().subtract(valor));

		return contaRepository.save(conta);

	}

	@Transactional
	public List<Conta> transferir(Integer contaIdEntrada, BigDecimal valor, Integer contaIdSaida) {
		
		Conta contaEntrada = contaRepository.findById(contaIdEntrada)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));
		Conta contaSaida = contaRepository.findById(contaIdSaida)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));
		
		if (valor.signum() <= 0)
			throw new ValorMenorQueZeroException("O valor para realizar a transferência deve ser maior do que 0");
		if (contaEntrada.getSaldo().compareTo(valor) < 0)
			throw new SaldoInsuficienteException("Saldo insuficiente na conta para realizar a transferência");

		contaEntrada.setSaldo(contaEntrada.getSaldo().subtract(valor));
		contaSaida.setSaldo(contaSaida.getSaldo().add(valor));

		contaRepository.save(contaEntrada);
		contaRepository.save(contaSaida);
		
		List<Conta> contas = new ArrayList<>();
		contas.add(contaEntrada);
		contas.add(contaSaida);

		return contas;
	}

	public BigDecimal simularRendimento(LocalDate dataPrevista, Integer id) {

		LocalDate dataAtual = LocalDate.now();
		long meses = ChronoUnit.MONTHS.between(dataAtual, dataPrevista);
		if (dataPrevista.isBefore(dataAtual))
			throw new DataInvalidaException("Para calcular o rendimento deve-se digitar uma data após o dia de hoje!");

		if (meses < 1)
			throw new DataInvalidaException(
					"Para calcular o rendimento deve-se digitar uma data acima de 1 mês da data atual!");

		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado para o ID: " + id));

		ContaPoupanca contaPoupanca = cliente.getContaPoupanca();
		if (contaPoupanca == null)
			throw new ContaInexistenteException("Este cliente não possui conta poupança");

		BigDecimal valorSimulado = contaPoupanca.simularRendimento(new BigDecimal(0.89), meses);
		System.out.printf("O Rendimento por %d meses será de $%f reais%n", meses, valorSimulado);

		return valorSimulado;
	}

	public List<Conta> buscarContasPorCliente(Cliente cliente) {
		return cliente.getContas().stream().toList();
	}

	@Transactional
	public Conta criarConta(ContaRequestDTO contaRequestDTO) {

		Cliente cliente = clienteRepository.findById(contaRequestDTO.clienteId())
				.orElseThrow(() -> new ClienteNaoEncontradoException(
						"Cliente não encontrado com o ID: " + contaRequestDTO.clienteId()));

		Conta novaConta;
		if ("CC".equalsIgnoreCase(contaRequestDTO.tipoConta())) {
			novaConta = new ContaCorrente();
		} else if ("CP".equalsIgnoreCase(contaRequestDTO.tipoConta())) {
			novaConta = new ContaPoupanca();
		} else {
			throw new TipoDeContaInvalidaException(
					"Tipo de conta inválido. Use 'CC' para Conta Corrente ou 'CP' para Conta Poupança.");
		}

		novaConta.setCliente(cliente);
		novaConta.setSaldo(BigDecimal.ZERO);
		cliente.adicionarNovaConta(novaConta);
		return contaRepository.save(novaConta);
	}

}
