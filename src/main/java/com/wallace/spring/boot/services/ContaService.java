package com.wallace.spring.boot.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ContaService.class);

	private final ClienteRepository clienteRepository;
	private final ContaRepository contaRepository;
	private final HistoricoContaService historicoContaService;

	@Value("${rendimento.poupanca.taxa-mensal:0.5}")
	private BigDecimal taxaRendimentoMensal;

	public ContaService(ClienteRepository clienteRepository, ContaRepository contaRepository,
			HistoricoContaService historicoContaService,
			@Value("${rendimento.poupanca.taxa-mensal:0.0089}") BigDecimal taxaRendimentoMensal) {
		this.clienteRepository = clienteRepository;
		this.contaRepository = contaRepository;
		this.historicoContaService = historicoContaService;
		this.taxaRendimentoMensal = taxaRendimentoMensal;
	}

	@Transactional
	public Conta depositar(BigDecimal valor, Integer id) {
        logger.info("Iniciando depósito de {} na conta ID {}", valor, id);
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));
		if (valor.signum() <= 0) {
            logger.warn("Tentativa de depósito com valor inválido: {}", valor);
			throw new ValorMenorQueZeroException("O valor para depósito deve ser maior do que 0 ");
        }
		conta.setSaldo(conta.getSaldo().add(valor));
		historicoContaService.registrarDeposito(conta, valor);
        logger.info("Depósito na conta ID {} realizado com sucesso. Novo saldo: {}", id, conta.getSaldo());
		return contaRepository.save(conta);
	}

	@Transactional
	public Conta sacar(BigDecimal valor, Integer id) {
        logger.info("Iniciando saque de {} da conta ID {}", valor, id);
		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));

		if (valor.signum() <= 0) {
            logger.warn("Tentativa de saque com valor inválido: {}", valor);
			throw new ValorMenorQueZeroException("O valor do saque deve ser maior que zero.");
		}

		if (conta.getSaldo().compareTo(valor) < 0) {
            logger.warn("Tentativa de saque com saldo insuficiente na conta ID {}. Saldo: {}, Saque: {}", id, conta.getSaldo(), valor);
			throw new SaldoInsuficienteException("Saldo insuficiente.");
		}

		historicoContaService.registrarSaque(conta, valor);
		conta.setSaldo(conta.getSaldo().subtract(valor));
        logger.info("Saque da conta ID {} realizado com sucesso. Novo saldo: {}", id, conta.getSaldo());
		return contaRepository.save(conta);
	}

	@Transactional
	public List<Conta> transferir(Integer contaIdDepositar, BigDecimal valor, Integer contaIdReceber) {
        logger.info("Iniciando transferência de {} da conta ID {} para a conta ID {}", valor, contaIdDepositar, contaIdReceber);
		Conta contaDepositar = contaRepository.findById(contaIdDepositar)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));
		Conta contaReceber = contaRepository.findById(contaIdReceber)
				.orElseThrow(() -> new ContaInexistenteException("Esta conta não existe!"));

		if (valor.signum() <= 0) {
            logger.warn("Tentativa de transferência com valor inválido: {}", valor);
			throw new ValorMenorQueZeroException("O valor para realizar a transferência deve ser maior do que 0");
        }
		if (contaDepositar.getSaldo().compareTo(valor) < 0) {
            logger.warn("Tentativa de transferência com saldo insuficiente na conta de origem ID {}. Saldo: {}, Transferência: {}", contaIdDepositar, contaDepositar.getSaldo(), valor);
			throw new SaldoInsuficienteException("Saldo insuficiente na conta para realizar a transferência");
        }

		contaDepositar.setSaldo(contaDepositar.getSaldo().subtract(valor));
		contaReceber.setSaldo(contaReceber.getSaldo().add(valor));

		contaRepository.save(contaDepositar);
		contaRepository.save(contaReceber);

		List<Conta> contas = new ArrayList<>();
		contas.add(contaDepositar);
		contas.add(contaReceber);

		historicoContaService.registrarTransferencia(contaDepositar, valor, contaReceber);
        logger.info("Transferência de {} da conta ID {} para a conta ID {} realizada com sucesso.", valor, contaIdDepositar, contaIdReceber);

		return contas;
	}

	public BigDecimal simularRendimento(LocalDate dataPrevista, Integer id) {
        logger.info("Iniciando simulação de rendimento para a conta ID {} com data prevista para {}", id, dataPrevista);
		LocalDate dataAtual = LocalDate.now();

		if (dataPrevista.isBefore(dataAtual)) {
            logger.warn("Data de simulação inválida (anterior a hoje): {}", dataPrevista);
			throw new DataInvalidaException("A data para simulação não pode ser anterior à data de hoje.");
		}

		if (ChronoUnit.MONTHS.between(dataAtual, dataPrevista) < 1) {
            logger.warn("Data de simulação inválida (menos de um mês a partir de hoje): {}", dataPrevista);
			throw new DataInvalidaException(
					"Para calcular o rendimento, a data prevista deve ter pelo menos um mês completo a partir de hoje.");
		}

		Conta conta = contaRepository.findById(id)
				.orElseThrow(() -> new ContaInexistenteException("Conta não encontrada para o ID: " + id));

		if (!(conta instanceof ContaPoupanca)) {
            logger.warn("Tentativa de simular rendimento para uma conta que não é poupança. ID: {}", id);
			throw new TipoDeContaInvalidaException("A simulação de rendimento é aplicável apenas a contas poupança.");
		}

		ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
		long meses = ChronoUnit.MONTHS.between(dataAtual, dataPrevista);
		BigDecimal valorSimulado = contaPoupanca.simularRendimento(taxaRendimentoMensal, meses);
        logger.info("Simulação de rendimento para conta ID {} concluída. Valor simulado: {}", id, valorSimulado);
		return valorSimulado;
	}

	public List<Conta> buscarContasPorCliente(Cliente cliente) {
        logger.info("Buscando contas para o cliente ID {}", cliente.getId());
		return cliente.getContas().stream().toList();
	}

	@Transactional
	public Conta criarConta(ContaRequestDTO contaRequestDTO) {
        logger.info("Iniciando criação de conta do tipo {} para o cliente ID {}", contaRequestDTO.tipoConta(), contaRequestDTO.clienteId());
		Cliente cliente = clienteRepository.findById(contaRequestDTO.clienteId())
				.orElseThrow(() -> new ClienteNaoEncontradoException(
						"Cliente não encontrado com o ID: " + contaRequestDTO.clienteId()));

		Conta novaConta;
		if ("CC".equalsIgnoreCase(contaRequestDTO.tipoConta())) {
			novaConta = new ContaCorrente();
		} else if ("CP".equalsIgnoreCase(contaRequestDTO.tipoConta())) {
			novaConta = new ContaPoupanca();
		} else {
            logger.warn("Tipo de conta inválido fornecido: {}", contaRequestDTO.tipoConta());
			throw new TipoDeContaInvalidaException(
					"Tipo de conta inválido. Use 'CC' para Conta Corrente ou 'CP' para Conta Poupança.");
		}

		novaConta.setCliente(cliente);
		novaConta.setSaldo(BigDecimal.ZERO);
		cliente.adicionarNovaConta(novaConta);
        Conta contaSalva = contaRepository.save(novaConta);
        logger.info("Conta do tipo {} criada com sucesso para o cliente ID {}. ID da nova conta: {}", contaRequestDTO.tipoConta(), contaRequestDTO.clienteId(), contaSalva.getId());
		return contaSalva;
	}
}