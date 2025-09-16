package com.wallace.spring.boot.domain.services;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wallace.spring.boot.dto.ClienteRequestDTO;
import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.exceptions.CpfJaExistenteException;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.repository.ClienteRepository;
import com.wallace.spring.boot.services.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

	@Mock
	private ClienteRepository clienteRepository;

	@InjectMocks
	private ClienteService clienteService;

	@Captor
	private ArgumentCaptor<Cliente> clienteCaptor;

	@Test
	@DisplayName("Deve buscar um cliente com sucesso")
	void deveBuscarUmClienteComSucesso() {
		String cpf = "123.456.789-00";
		Cliente clienteMock = new Cliente("Wallace", cpf);

		when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.of(clienteMock));

		Cliente cliente = clienteService.buscarClientePorCPF(cpf);

		assertNotNull(cliente);
		assertEquals(clienteMock.getNome(), cliente.getNome());
		assertEquals(clienteMock.getCpf(), cliente.getCpf());

		verify(clienteRepository).findByCpf(cpf);
	}

	@Test
	@DisplayName("Deve lançar exceção ao buscar cliente com CPF inexistente")
	void deveLancarExcecaoAoBuscarClientePorCpfInexistente() {
		String cpfInexistente = "000.000.000-00";

		when(clienteRepository.findByCpf(cpfInexistente)).thenReturn(Optional.empty());

		assertThrows(ClienteNaoEncontradoException.class, () -> {
			clienteService.buscarClientePorCPF(cpfInexistente);
		});

		verify(clienteRepository).findByCpf(cpfInexistente);
	}

	@Test
	@DisplayName("Deve cadastrar o cliente com sucesso")
	void DeveCadastrarClienteComSucesso() {
		String cpf = "123.456.789-00";
		ClienteRequestDTO clienteMock = new ClienteRequestDTO("Wallace", cpf);

		when(clienteRepository.findByCpf(clienteMock.cpf())).thenReturn(Optional.empty());

		clienteService.cadastrarCliente(clienteMock);

		verify(clienteRepository).findByCpf(cpf);
		verify(clienteRepository).save(clienteCaptor.capture());
		Cliente clientePersistido = clienteCaptor.getValue();

		assertEquals(clienteMock.nome(), clientePersistido.getNome());
		assertEquals(clienteMock.cpf(), clientePersistido.getCpf());
	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar cadastrar cliente com CPF já existente")
	void deveLancarExcecaoAoCadastrarClienteComCpfRepetido() {

		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Outro Cliente", "555.666.777-88");

		Cliente clienteExistente = new Cliente("Cliente Antigo", clienteRequestDTO.cpf());

		when(clienteRepository.findByCpf(clienteRequestDTO.cpf())).thenReturn(Optional.of(clienteExistente));

		assertThrows(CpfJaExistenteException.class, () -> {
			clienteService.cadastrarCliente(clienteRequestDTO);
		});

		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve alterar o nome e o CPF do cliente com sucesso")
	void deveAlterarDadosClienteComSucesso() {

		String cpfOriginal = "123.456.789-00";
		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Wallace Atualizado", "000.000.000-27");

		Cliente clienteOriginal = new Cliente("Wallace Original", cpfOriginal);

		when(clienteRepository.findByCpf(cpfOriginal)).thenReturn(Optional.of(clienteOriginal));

		when(clienteRepository.findByCpf(clienteRequestDTO.cpf())).thenReturn(Optional.empty());

		Cliente clienteAlterado = clienteService.alterarDadosClientePorCPF(cpfOriginal, clienteRequestDTO);

		assertNotNull(clienteAlterado);
		assertEquals(clienteRequestDTO.nome(), clienteAlterado.getNome(),
				"O nome do cliente não foi atualizado corretamente.");
		assertEquals(clienteRequestDTO.cpf(), clienteAlterado.getCpf(),
				"O CPF do cliente não foi atualizado corretamente.");

		verify(clienteRepository).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve alterar apenas o nome do cliente com sucesso")
	void deveAlterarApenasNomeClienteComSucesso() {

		String cpfOriginal = "123.456.789-00";
		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Apenas o Nome MUDOU", cpfOriginal);

		Cliente clienteOriginal = new Cliente("Nome Antigo", cpfOriginal);

		when(clienteRepository.findByCpf(cpfOriginal)).thenReturn(Optional.of(clienteOriginal));

		Cliente clienteAlterado = clienteService.alterarDadosClientePorCPF(cpfOriginal, clienteRequestDTO);

		assertNotNull(clienteAlterado);
		assertEquals(clienteAlterado.getNome(), clienteRequestDTO.nome());
		assertEquals(clienteAlterado.getCpf(), clienteRequestDTO.cpf());

		verify(clienteRepository).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar alterar dados de um cliente com CPF original inexistente")
	void deveLancarExcecaoAoAlterarClienteComCpfOriginalInexistente() {

		String cpfInexistente = "123.456.789-00";
		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Wallace Atualizado", "000.000.000-27");

		when(clienteRepository.findByCpf(cpfInexistente)).thenReturn(Optional.empty());

		assertThrows(ClienteNaoEncontradoException.class,
				() -> clienteService.alterarDadosClientePorCPF(cpfInexistente, clienteRequestDTO));

		verify(clienteRepository, never()).save(any(Cliente.class));

	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar alterar cliente para CPF que já está em uso")
	void deveLancarExcecaoAoAlterarClienteParaCpfJaExistente() {

		String cpfAntigo = "111.222.333-44";
		Cliente clienteOriginal = new Cliente("Wallace Original", cpfAntigo);

		String cpfNovoJaExistente = "999.888.777-66";
		ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO("Wallace Atualizado", cpfNovoJaExistente);

		Cliente outroClienteComCpfNovo = new Cliente("Ronaldo", cpfNovoJaExistente);

		when(clienteRepository.findByCpf(cpfAntigo)).thenReturn(Optional.of(clienteOriginal));

		when(clienteRepository.findByCpf(cpfNovoJaExistente)).thenReturn(Optional.of(outroClienteComCpfNovo));

		assertThrows(CpfJaExistenteException.class, () -> {
			clienteService.alterarDadosClientePorCPF(cpfAntigo, clienteRequestDTO);
		});

		verify(clienteRepository, never()).save(any(Cliente.class));
	}

	@Test
	@DisplayName("Deve deletar o cliente pelo cpf com sucesso")
	void deveDeletarClienteComSucesso() {
		String cpfParaDeletar = "123.456.789-00";
		Cliente clienteExistente = new Cliente("Cliente Para Deletar", cpfParaDeletar);

		when(clienteRepository.findByCpf(cpfParaDeletar)).thenReturn(Optional.of(clienteExistente));

		clienteService.deletarClientePorCpf(cpfParaDeletar);

		verify(clienteRepository).delete(clienteExistente);
	}

	@Test
	@DisplayName("Deve lançar exceção ao tentar deletar dados de um cliente com CPF original inexistente")
	void DeveLançarExceçãoAoTentarDeletarUmClienteComCpfOriginalInexistente() {
		String cpfParaDeletar = "123.456.789-00";

		when(clienteRepository.findByCpf(cpfParaDeletar)).thenReturn(Optional.empty());

		assertThrows(ClienteNaoEncontradoException.class, () -> clienteService.deletarClientePorCpf(cpfParaDeletar));

		verify(clienteRepository, never()).delete(any(Cliente.class));

	}

}