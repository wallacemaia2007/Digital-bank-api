package com.wallace.spring.boot.domain.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallace.spring.boot.controller.ClienteController;
import com.wallace.spring.boot.dto.ClienteRequestDTO;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.services.ClienteService;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

	@InjectMocks
	private ClienteController clienteController;

	@Mock
	private ClienteService clienteService;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
	}

	@Test
	void deveRetornarTodosOsClientes() throws Exception {
		Cliente cliente1 = new Cliente("Wallace", "123.456.789-00");
		cliente1.setId(1);
		Cliente cliente2 = new Cliente("Maria", "987.654.321-00");
		cliente2.setId(2);

		when(clienteService.buscarTodosClientes()).thenReturn(List.of(cliente1, cliente2));

		mockMvc.perform(get("/clientes")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].nome").value("Wallace"))
				.andExpect(jsonPath("$[0].cpf").value("123.456.789-00")).andExpect(jsonPath("$[1].id").value(2))
				.andExpect(jsonPath("$[1].nome").value("Maria"))
				.andExpect(jsonPath("$[1].cpf").value("987.654.321-00"));

		verify(clienteService, times(1)).buscarTodosClientes();
	}

	@Test
	void deveRetornarClientePeloCpf() throws Exception {
		Cliente cliente = new Cliente("Wallace", "123.456.789-00");
		cliente.setId(1);

		when(clienteService.buscarClientePorCPF(cliente.getCpf())).thenReturn(cliente);

		mockMvc.perform(get("/clientes/{cpf}", cliente.getCpf())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.nome").value("Wallace"))
				.andExpect(jsonPath("$.cpf").value("123.456.789-00"));

		verify(clienteService, times(1)).buscarClientePorCPF(cliente.getCpf());
	}

	@Test
	void deveCadastrarClienteNovo() throws Exception {
		Cliente cliente = new Cliente("Wallace", "123.456.789-00");
		cliente.setId(1);

		
		ObjectMapper mapper = new ObjectMapper();
		ClienteRequestDTO dto = new ClienteRequestDTO("Wallace", "123.456.789-00");
		String json = mapper.writeValueAsString(dto);

		when(clienteService.cadastrarCliente(dto)).thenReturn(cliente);

		mockMvc.perform(post("/clientes").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.nome").value("Wallace"))
				.andExpect(jsonPath("$.cpf").value("123.456.789-00"));

		verify(clienteService, times(1)).cadastrarCliente(dto);
	}

	@Test
	void deveDeletarCliente() throws Exception {
		String cpf = "123.456.789-00";

		doNothing().when(clienteService).deletarClientePorCpf(cpf);

		mockMvc.perform(delete("/clientes/{cpf}", cpf)).andExpect(status().isNoContent());

		verify(clienteService, times(1)).deletarClientePorCpf(cpf);
	}

	@Test
	void deveAlterarDadosCliente() throws Exception {
		String cpf = "123.456.789-00";
		
		ClienteRequestDTO clienteAlteradoDTO = new ClienteRequestDTO("Levi", "000.111.222-00");

		Cliente clienteAlterado = new Cliente("Levi", "000.111.222-00");
		clienteAlterado.setId(1);

		when(clienteService.alterarDadosClientePorCPF(cpf, clienteAlteradoDTO)).thenReturn(clienteAlterado);

		String json = """
				    {
				        "nome": "Levi",
				        "cpf": "000.111.222-00"
				    }
				""";

		mockMvc.perform(put("/clientes/{cpf}", cpf).contentType("application/json").content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nome").value("Levi")).andExpect(jsonPath("$.cpf").value("000.111.222-00"));

		verify(clienteService, times(1)).alterarDadosClientePorCPF(cpf, clienteAlteradoDTO);
	}

}
