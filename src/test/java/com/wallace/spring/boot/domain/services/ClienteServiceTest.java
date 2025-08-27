package com.wallace.spring.boot.domain.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wallace.spring.boot.dto.ClienteRequestDTO;
import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.exceptions.CpfJaExistenteException;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.repository.ClienteRepository;
import com.wallace.spring.boot.model.services.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteService clienteService;

	@Test
	@DisplayName("Deve buscar um cliente com sucesso")
	void deveBuscarUmClienteComSucesso() {
		String cpf = "123.456.789-00";
		Cliente clienteMock = new Cliente("Wallace", cpf);

		Mockito.when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(clienteMock));

		Cliente cliente = clienteService.buscarClientePorCPF(cpf);

		Assertions.assertNotNull(cliente);
		Assertions.assertEquals(clienteMock.getNome(), cliente.getNome());
		Assertions.assertEquals(clienteMock.getCpf(), cliente.getCpf());

		Mockito.verify(clienteRepository).findByCpf(cpf);
	}

	@Test
	@DisplayName("Deve lançar exceção ao buscar cliente com CPF inexistente")
	void deveLancarExcecaoAoBuscarClientePorCpfInexistente() {
		String cpfInexistente = "000.000.000-00";

		Mockito.when(clienteRepository.findByCpf(cpfInexistente)).thenReturn(Optional.empty());

		Assertions.assertThrows(ClienteNaoEncontradoException.class, () -> {
			clienteService.buscarClientePorCPF(cpfInexistente);
		});

		Mockito.verify(clienteRepository).findByCpf(cpfInexistente);
	}

	@Test
	@DisplayName("Deve cadastrar o cliente com sucesso")
	void DeveCadastrarClienteComSucesso() {
		String cpf = "123.456.789-00";
		ClienteRequestDTO clienteMock = new ClienteRequestDTO("Wallace", cpf);

		Mockito.when(clienteRepository.findByCpf(clienteMock.cpf())).thenReturn(Optional.empty());

		Cliente clienteSalvo = clienteService.cadastrarCliente(clienteMock);

		Assertions.assertNotNull(clienteSalvo);
		Assertions.assertEquals(clienteMock.nome(), clienteSalvo.getNome());
		Assertions.assertEquals(clienteMock.cpf(), clienteSalvo.getCpf());

		Mockito.verify(clienteRepository).save(Mockito.any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar cadastrar cliente com CPF já existente")
	void deveLancarExcecaoAoCadastrarClienteComCpfRepetido() {

		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Outro Cliente", "555.666.777-88");

		Cliente clienteExistente = new Cliente("Cliente Antigo", clienteRequestDTO.cpf());

		Mockito.when(clienteRepository.findByCpf(clienteRequestDTO.cpf())).thenReturn(Optional.of(clienteExistente));

		Assertions.assertThrows(CpfJaExistenteException.class, () -> {
			clienteService.cadastrarCliente(clienteRequestDTO);
		});

		Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
	}

	@Test
	@DisplayName("Deve alterar o nome e o CPF do cliente com sucesso")
	void deveAlterarDadosClienteComSucesso() {

		String cpfOriginal = "123.456.789-00";
		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Wallace Atualizado", "000.000.000-27");

		Cliente clienteOriginal = new Cliente("Wallace Original", cpfOriginal);

		Mockito.when(clienteRepository.findByCpf(cpfOriginal)).thenReturn(Optional.of(clienteOriginal));

		Mockito.when(clienteRepository.findByCpf(clienteRequestDTO.cpf())).thenReturn(Optional.empty());

		Cliente clienteAlterado = clienteService.alterarDadosClientePorCPF(cpfOriginal, clienteRequestDTO);

		Assertions.assertNotNull(clienteAlterado);
		Assertions.assertEquals(clienteRequestDTO.nome(), clienteAlterado.getNome(),
				"O nome do cliente não foi atualizado corretamente.");
		Assertions.assertEquals(clienteRequestDTO.cpf(), clienteAlterado.getCpf(),
				"O CPF do cliente não foi atualizado corretamente.");

		Mockito.verify(clienteRepository).save(Mockito.any(Cliente.class));
	}

	@Test
	@DisplayName("Deve alterar apenas o nome do cliente com sucesso")
	void deveAlterarApenasNomeClienteComSucesso() {

		String cpfOriginal = "123.456.789-00";
		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Apenas o Nome MUDOU", cpfOriginal);

		Cliente clienteOriginal = new Cliente("Nome Antigo", cpfOriginal);

		Mockito.when(clienteRepository.findByCpf(cpfOriginal)).thenReturn(Optional.of(clienteOriginal));

		Cliente clienteAlterado = clienteService.alterarDadosClientePorCPF(cpfOriginal, clienteRequestDTO);

		Assertions.assertNotNull(clienteAlterado);
		Assertions.assertEquals(clienteAlterado.getNome(), clienteRequestDTO.nome());
		Assertions.assertEquals(clienteAlterado.getCpf(), clienteRequestDTO.cpf());

		Mockito.verify(clienteRepository).save(Mockito.any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar alterar dados de um cliente com CPF original inexistente")
	void deveLancarExcecaoAoAlterarClienteComCpfOriginalInexistente() {

		String cpfInexistente = "123.456.789-00";
		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Wallace Atualizado", "000.000.000-27");

		Mockito.when(clienteRepository.findByCpf(cpfInexistente)).thenReturn(Optional.empty());

		Assertions.assertThrows(ClienteNaoEncontradoException.class,
				() -> clienteService.alterarDadosClientePorCPF(cpfInexistente, clienteRequestDTO));

		Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));

	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar alterar cliente para CPF que já está em uso")
	void deveLancarExcecaoAoAlterarClienteParaCpfJaExistente() {

		String cpfAntigo = "111.222.333-44";
		Cliente clienteOriginal = new Cliente("Wallace Original", cpfAntigo);

		String cpfNovoJaExistente = "999.888.777-66";
		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Wallace Atualizado", cpfNovoJaExistente);

		Cliente outroClienteComCpfNovo = new Cliente("Ronaldo", cpfNovoJaExistente);

		Mockito.when(clienteRepository.findByCpf(cpfAntigo)).thenReturn(Optional.of(clienteOriginal));

		Mockito.when(clienteRepository.findByCpf(cpfNovoJaExistente)).thenReturn(Optional.of(outroClienteComCpfNovo));

		Assertions.assertThrows(CpfJaExistenteException.class, () -> {
			clienteService.alterarDadosClientePorCPF(cpfAntigo, clienteRequestDTO);
		});

		Mockito.verify(clienteRepository, Mockito.never()).save(Mockito.any(Cliente.class));
	}

	@Test
	@DisplayName("Deve deletar o cliente pelo cpf com sucesso")
	void deveDeletarClienteComSucesso() {
		String cpfParaDeletar = "123.456.789-00";
		Cliente clienteExistente = new Cliente("Cliente Para Deletar", cpfParaDeletar);

		Mockito.when(clienteRepository.findByCpf(cpfParaDeletar)).thenReturn(Optional.of(clienteExistente));

		clienteService.deletarClientePorCpf(cpfParaDeletar);

		Mockito.verify(clienteRepository).delete(clienteExistente);
	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar deletar dados de um cliente com CPF original inexistente")
	void DeveLançarExceçãoAoTentarDeletarUmClienteComCpfOriginalInexistente() {
		String cpfParaDeletar = "123.456.789-00";

		Mockito.when(clienteRepository.findByCpf(cpfParaDeletar)).thenReturn(Optional.empty());

		Assertions.assertThrows(ClienteNaoEncontradoException.class,
				() -> clienteService.deletarClientePorCpf(cpfParaDeletar));

		Mockito.verify(clienteRepository, Mockito.never()).delete(Mockito.any(Cliente.class));

	}

}