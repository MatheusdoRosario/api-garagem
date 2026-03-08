package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.dto.CadastroCarroDTO;
import com.rosario.garagem_automotiva.dto.CadastroManutencaoDTO;
import com.rosario.garagem_automotiva.dto.CarroDTO;
import com.rosario.garagem_automotiva.dto.ManutencaoDTO;
import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.Manutencao;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.service.ManutencaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ManutencaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ManutencaoService service;

    @Autowired
    private JacksonTester<CadastroManutencaoDTO> cadastroJson;

    @Autowired
    private JacksonTester<ManutencaoDTO> manutencaoJson;

    @Test
    void deveRetornar200AoListarPorCarroId() throws Exception {
        var response = mvc.perform(
                get("/api/v1/manutencoes/" + UUID.randomUUID())
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar200AoListarPorPeriodo() throws Exception {
        var response = mvc.perform(
                get("/api/v1/manutencoes/periodo")
                        .param("inicio" , String.valueOf(LocalDate.now()))
                        .param("fim" , String.valueOf(LocalDate.now()))
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar201AoCadastrarManutencaoSemErro() throws Exception {
        CadastroManutencaoDTO dto = new CadastroManutencaoDTO(2000, "Cabeçote", LocalDate.now(), mock(Carro.class));

        var response = mvc.perform(
                post("/api/v1/manutencoes")
                        .content(cadastroJson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornar200AoAtualizarManutencaoSemErro() throws Exception {
        ManutencaoDTO dto = new ManutencaoDTO(UUID.randomUUID() ,2000, "Cabeçote", LocalDate.now(), mock(Carro.class));

        var response = mvc.perform(
                put("/api/v1/manutencoes")
                        .content(manutencaoJson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar404AoAtualizarManutencaoInexistente() throws Exception {
        ManutencaoDTO dto = new ManutencaoDTO(UUID.randomUUID() ,2000, "Cabeçote", LocalDate.now(), mock(Carro.class));
        Mockito.doThrow(new ValidacaoException("Manutenção não encontrada"))
                .when(service).atualizarManutencao(any(ManutencaoDTO.class));

        var response = mvc.perform(
                put("/api/v1/manutencoes")
                        .content(manutencaoJson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveRetornar204AoExcluirManutencaoSemErro() throws Exception {
        var response = mvc.perform(
                delete("/api/v1/manutencoes/" + UUID.randomUUID())
        ).andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void deveRetornar404AoExcluirManutencaoInexistente() throws Exception {
        var response = mvc.perform(
                delete("/api/v1/manutencoes/")
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

}