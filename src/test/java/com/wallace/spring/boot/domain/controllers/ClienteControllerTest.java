package com.wallace.spring.boot.domain.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallace.spring.boot.controller.ClienteController;
import com.wallace.spring.boot.dto.ClienteRequestDTO;
import com.wallace.spring.boot.exceptions.ClienteNaoEncontradoException;
import com.wallace.spring.boot.exceptions.CpfInvalidoException;
import com.wallace.spring.boot.exceptions.CpfJaExistenteException;
import com.wallace.spring.boot.exceptions.GlobalExceptionHandler;
import com.wallace.spring.boot.model.entities.Cliente;
import com.wallace.spring.boot.model.services.ClienteService;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    private MockMvc mockMvc;

    private Cliente cliente;

    private ObjectMapper mapper;

    private ClienteRequestDTO clienteRequestDTO;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        cliente = new Cliente("Wallace", "123.456.789-00");
        cliente.setId(1);

		clienteRequestDTO = new ClienteRequestDTO(cliente.getNome(), cliente.getCpf());

        mapper = new ObjectMapper();
    }

    @Test
    void deveRetornarTodosOsClientes() throws Exception {
        Cliente cliente2 = new Cliente("Maria", "987.654.321-00");
        cliente2.setId(2);

        when(clienteService.buscarTodosClientes()).thenReturn(List.of(cliente, cliente2));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Wallace"))
                .andExpect(jsonPath("$[0].cpf").value("123.456.789-00"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Maria"))
                .andExpect(jsonPath("$[1].cpf").value("987.654.321-00"));

        verify(clienteService, times(1)).buscarTodosClientes();
    }

    @Test
    void deveRetornarClientePeloCpf() throws Exception {
        when(clienteService.buscarClientePorCPF(cliente.getCpf())).thenReturn(cliente);

        mockMvc.perform(get("/clientes/{cpf}", cliente.getCpf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Wallace"))
                .andExpect(jsonPath("$.cpf").value("123.456.789-00"));

        verify(clienteService, times(1)).buscarClientePorCPF(cliente.getCpf());
    }

    @Test
    void deveCadastrarClienteNovo() throws Exception {
        when(clienteService.cadastrarCliente(any(ClienteRequestDTO.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(clienteRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Wallace"))
                .andExpect(jsonPath("$.cpf").value("123.456.789-00"));

        verify(clienteService, times(1)).cadastrarCliente(any(ClienteRequestDTO.class));
    }

    @Test
    void deveDeletarCliente() throws Exception {
        String cpf = "123.456.789-00";

        doNothing().when(clienteService).deletarClientePorCpf(cpf);

        mockMvc.perform(delete("/clientes/{cpf}", cpf))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).deletarClientePorCpf(cpf);
    }

    @Test
    void deveAlterarDadosCliente() throws Exception {
        String cpf = "123.456.789-00";

        Cliente clienteAlterado = new Cliente("Levi", "000.111.222-00");
        clienteAlterado.setId(1);

        when(clienteService.alterarDadosClientePorCPF(cpf, clienteRequestDTO)).thenReturn(clienteAlterado);

        mockMvc.perform(put("/clientes/{cpf}", cpf)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(clienteRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Levi"))
                .andExpect(jsonPath("$.cpf").value("000.111.222-00"));

        verify(clienteService, times(1)).alterarDadosClientePorCPF(cpf, clienteRequestDTO);
    }

    @Test
    void deveRetornar404AoBuscarClientePorCpfInexistente() throws Exception {
        String cpf = "123.456.789-00";

        when(clienteService.buscarClientePorCPF(cpf))
                .thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        mockMvc.perform(get("/clientes/{cpf}", cpf))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    void deveRetornar400AoCadastrarClienteComCpfInvalido() throws Exception {
        when(clienteService.cadastrarCliente(any(ClienteRequestDTO.class)))
                .thenThrow(new CpfInvalidoException("Cpf inválido"));

        mockMvc.perform(post("/clientes")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(clienteRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cpf inválido"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    void deveRetornar409AoCadastrarClienteJaExistente() throws Exception {
        when(clienteService.cadastrarCliente(any(ClienteRequestDTO.class)))
                .thenThrow(new CpfJaExistenteException("CPF já cadastrado"));

        mockMvc.perform(post("/clientes")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(clienteRequestDTO)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("CPF já cadastrado"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    void deveRetornar404AoDeletarClienteInexistente() throws Exception {
        String cpf = "100.100.200-23";

        doThrow(new ClienteNaoEncontradoException("Cliente não encontrado"))
                .when(clienteService).deletarClientePorCpf(cpf);

        mockMvc.perform(delete("/clientes/{cpf}", cpf))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    void deveRetornar404AoAlterarClienteInexistente() throws Exception {
        String cpf = "100.100.200-23";

        when(clienteService.alterarDadosClientePorCPF(cpf, clienteRequestDTO))
                .thenThrow(new ClienteNaoEncontradoException("Cliente não encontrado"));

        mockMvc.perform(put("/clientes/{cpf}", cpf)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(clienteRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.details").exists());
    }
}
