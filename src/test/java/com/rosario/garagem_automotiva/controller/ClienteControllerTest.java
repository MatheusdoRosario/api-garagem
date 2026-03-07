package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.dto.CadastroClienteDTO;
import com.rosario.garagem_automotiva.dto.ClienteDTO;
import com.rosario.garagem_automotiva.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClienteService service;

    @Autowired
    private JacksonTester<CadastroClienteDTO> cadastroJson;

    @Autowired
    private JacksonTester<ClienteDTO> clienteJson;

    @Test
    void deveRetornar200AoListarClientesPorNome() throws Exception {
        var response = mvc.perform(
                get("/api/v1/clientes/nome/matheus")
                ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar200AoListarClientesPorTelefone() throws Exception {
        var response = mvc.perform(
                get("/api/v1/clientes/telefone/22555554444")
                ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar201AoCadastrarClienteSemErro() throws Exception {
        CadastroClienteDTO cadastroClienteDTO = new CadastroClienteDTO("Matheus", "22555554444");

        var response = mvc.perform(
                post("/api/v1/clientes")
                        .content(cadastroJson.write(cadastroClienteDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornar400AoCadastrarClienteComJsonInvalido() throws Exception {
        String json = "{}";

        var response = mvc.perform(
                post("/api/v1/clientes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar200AoAtualizarClienteSemErro() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO(1L, "Matheus", "22555554444");

        var response = mvc.perform(
                put("/api/v1/clientes")
                        .content(clienteJson.write(clienteDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar400AoAtualizarClienteComJsonInvalido() throws Exception {
        String json = "{}";

        var response = mvc.perform(
                put("/api/v1/clientes")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

}