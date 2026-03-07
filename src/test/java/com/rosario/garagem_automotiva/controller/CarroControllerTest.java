package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.dto.CadastroCarroDTO;
import com.rosario.garagem_automotiva.dto.CarroDTO;
import com.rosario.garagem_automotiva.dto.ImagemCarroDTO;
import com.rosario.garagem_automotiva.dto.VenderCarroDTO;
import com.rosario.garagem_automotiva.entity.MarcaCarro;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.service.CarroService;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class CarroControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarroService service;

    @Autowired
    private JacksonTester<CadastroCarroDTO> cadastroJson;

    @Autowired
    private JacksonTester<CarroDTO> carroJson;

    @Autowired
    private JacksonTester<VenderCarroDTO> venderJson;

    @Test
    void deveRetornar200ParaTodosOsCarros() throws Exception {
        var response = mvc.perform(get("/api/v1/carros"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar200ParaTodosOsCarrosPorModelo() throws Exception {
        var response = mvc.perform(get("/api/v1/carros/modelo/Honda"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar200ParaTodosOsCarrosPorMarca() throws Exception {
        var response = mvc.perform(get("/api/v1/carros/marca/HONDA"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar200ParaTodosOsCarrosPorAno() throws Exception {
        var response = mvc.perform(get("/api/v1/carros/ano/2017"))
                .andReturn()
                .getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar201ParaCadastrarSemErro() throws Exception {
        CadastroCarroDTO cadastroCarroDTO = new CadastroCarroDTO
                (BigDecimal.valueOf(90000.00), "Honda civic", MarcaCarro.HONDA, 2017, "abc-2017");

        var response = mvc.perform(
                post("/api/v1/carros")
                        .content(cadastroJson.write(cadastroCarroDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornar200ParaAtualizarSemErro() throws Exception {
        List<ImagemCarroDTO> imagens = new ArrayList<>();
        CarroDTO carroDTO = new CarroDTO
                (UUID.randomUUID() , BigDecimal.valueOf(90000.00), "Honda civic",
                        MarcaCarro.HONDA, 2017, "abc-2017", false , imagens);

        var response = mvc.perform(
                put("/api/v1/carros")
                        .content(carroJson.write(carroDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar404ParaAtualizarCarroInexistente() throws Exception {
        List<ImagemCarroDTO> imagens = new ArrayList<>();
        CarroDTO carroDTO = new CarroDTO
                (UUID.randomUUID() , BigDecimal.valueOf(90000.00), "Honda civic",
                        MarcaCarro.HONDA, 2017, "abc-2017", false , imagens);

        Mockito.doThrow(new ValidacaoException("Carro não encontrado"))
                .when(service).atualizarCarro(any(CarroDTO.class));

        var response = mvc.perform(
                put("/api/v1/carros")
                        .content(carroJson.write(carroDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveRetornar200ParaVenderSemErro() throws Exception {
        VenderCarroDTO venderCarroDTO = new VenderCarroDTO(UUID.randomUUID(), 1L);

        var response = mvc.perform(
                put("/api/v1/carros/vender")
                        .content(venderJson.write(venderCarroDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveRetornar404ParaVenderCarroInexistente() throws Exception {
        VenderCarroDTO venderCarroDTO = new VenderCarroDTO(UUID.randomUUID(), 1L);

        Mockito.doThrow(new ValidacaoException("Carro não encontrado"))
                .when(service).marcarComoVendido(venderCarroDTO.carroId(), venderCarroDTO.vendedorId());

        var response = mvc.perform(
                put("/api/v1/carros/vender")
                        .content(venderJson.write(venderCarroDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }
}

