package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.dto.CadastroVendedorDTO;
import com.rosario.garagem_automotiva.dto.VendedorDTO;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.service.VendedorService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class VendedorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VendedorService service;

    @Autowired
    private JacksonTester<CadastroVendedorDTO> cadastroJson;

    @Autowired
    private JacksonTester<VendedorDTO> vendedorJson;

    @Test
    void deveRetornar200AoListarVendedores() throws Exception {
        var response = mvc.perform(
                get("/api/v1/vendedores")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar200AoListarResumoDeVendasPorVendedorSemErro() throws Exception {
        var response = mvc.perform(
                get("/api/v1/vendedores/vendas")
                        .param("vendedorId", "1")
                        .param("inicio" , String.valueOf(LocalDate.now()))
                        .param("fim" , String.valueOf(LocalDate.now()))
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar404AoListarResumoDeVendasPorVendedorInexistente() throws Exception {
        Mockito.doThrow(new ValidacaoException("Vendedor não encontrado"))
                .when(service).calcularVendas(anyLong(), any(LocalDate.class), any(LocalDate.class));

        var response = mvc.perform(
                get("/api/v1/vendedores/vendas")
                        .param("vendedorId", "1")
                        .param("inicio" , String.valueOf(LocalDate.now()))
                        .param("fim" , String.valueOf(LocalDate.now()))
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveRetornar201AoCadastrarVendedorSemErro() throws Exception {
        CadastroVendedorDTO dto = new CadastroVendedorDTO("Matheus", "22555554444");

        var response = mvc.perform(
                post("/api/v1/vendedores")
                        .content(cadastroJson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornar400AoCadastrarVendedorComJsonInvalido() throws Exception {
        String json = "{}";

        var response = mvc.perform(
                post("/api/v1/vendedores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar200AoAtualizarVendedorSemErro() throws Exception {
        VendedorDTO dto = new VendedorDTO(1L, "Matheus", "22555554444", true);

        var response = mvc.perform(
                put("/api/v1/vendedores")
                        .content(vendedorJson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar400AoAtualizarVendedorComJsonInvalido() throws Exception {
        String json = "{}";

        var response = mvc.perform(
                put("/api/v1/vendedores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar200AoDesativarVendedorSemErro() throws Exception {
        var response = mvc.perform(
                put("/api/v1/vendedores/desativar/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar400AoDesativarVendedorDesativado() throws Exception {
        Mockito.doThrow(new ValidacaoException("Vendedor já desativado"))
                .when(service).desativarVendedor(1L);

        var response = mvc.perform(
                put("/api/v1/vendedores/desativar/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar200AoAtivarVendedorSemErro() throws Exception {
        var response = mvc.perform(
                put("/api/v1/vendedores/ativar/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar400AoAtivarVendedorAtivado() throws Exception {
        Mockito.doThrow(new ValidacaoException("Vendedor já ativado"))
                .when(service).ativarVendedor(1L);

        var response = mvc.perform(
                put("/api/v1/vendedores/ativar/1")
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }
}