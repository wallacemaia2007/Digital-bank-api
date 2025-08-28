package com.wallace.spring.boot.model.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${rendimento.poupanca.taxa-mensal:0.5}")
	private BigDecimal taxaRendimentoMensal;
	
	public ContaService(
			ClienteRepository clienteRepository, 
			ContaRepository contaRepository,
			@Value("${rendimento.poupanca.taxa-mensal:0.0089}") BigDecimal taxaRendimentoMensal) {
		this.clienteRepository = clienteRepository;
		this.contaRepository = contaRepository;
		this.taxaRendimentoMensal = taxaRendimentoMensal;
	}

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
	public List<Conta> transferir(Integer contaIdDepositar, BigDecimal valor, Integer contaIdReceber) {

		Conta contaDepositar = contaRepository.findById(contaIdDepositar)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));
		Conta contaReceber = contaRepository.findById(contaIdReceber)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));

		if (valor.signum() <= 0)
			throw new ValorMenorQueZeroException("O valor para realizar a transferência deve ser maior do que 0");
		if (contaDepositar.getSaldo().compareTo(valor) < 0)
			throw new SaldoInsuficienteException("Saldo insuficiente na conta para realizar a transferência");

		contaDepositar.setSaldo(contaDepositar.getSaldo().subtract(valor));
		contaReceber.setSaldo(contaReceber.getSaldo().add(valor));

		contaRepository.save(contaDepositar);
		contaRepository.save(contaReceber);

		List<Conta> contas = new ArrayList<>();
		contas.add(contaDepositar);
		contas.add(contaReceber);

		return contas;
	}

	public BigDecimal simularRendimento(LocalDate dataPrevista, Integer id) {
		LocalDate dataAtual = LocalDate.now();

		if (dataPrevista.isBefore(dataAtual)) {
			throw new DataInvalidaException("A data para simulação não pode ser anterior à data de hoje.");
		}

		if (ChronoUnit.MONTHS.between(dataAtual, dataPrevista) < 1) {
			throw new DataInvalidaException(
					"Para calcular o rendimento, a data prevista deve ter pelo menos um mês completo a partir de hoje.");
		}

		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ContaInexistenteException("Conta não encontrada para o ID: " + id));

		if (!(conta instanceof ContaPoupanca)) {
			throw new TipoDeContaInvalidaException("A simulação de rendimento é aplicável apenas a contas poupança.");
		}

		ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
		long meses = ChronoUnit.MONTHS.between(dataAtual, dataPrevista);
		BigDecimal valorSimulado = contaPoupanca.simularRendimento(taxaRendimentoMensal, meses);

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
