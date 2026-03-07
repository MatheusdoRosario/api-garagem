package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.service.ImagemCarroService;
import com.rosario.garagem_automotiva.service.StorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ImagemCarroControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ImagemCarroService imagemCarroService;

    @MockBean
    private StorageService storageService;

    @Test
    void deveRetornar201AoSalvarImagemSemErro() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file" , "teste.jpg", MediaType.IMAGE_JPEG_VALUE, "teste".getBytes());
        when(storageService.salvarArquivo(any())).thenReturn("/uploads/teste");

        var response = mvc.perform(
                multipart("/api/v1/imagens/upload")
                        .file(file)
                        .param("carroId", String.valueOf(UUID.randomUUID()))
        ).andReturn().getResponse();

        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void deveRetornar400AoSalvarImagemVazia() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file" , "teste.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0]);

        var response = mvc.perform(
                multipart("/api/v1/imagens/upload")
                        .file(file)
                        .param("carroId", String.valueOf(UUID.randomUUID()))
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveRetornar204AoExcluirImagemSemErro() throws Exception {
        var response = mvc.perform(
                delete("/api/v1/imagens/" + UUID.randomUUID())
        ).andReturn().getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void deveRetornar404AoExcluirImagemInexistente() throws Exception {
        doThrow(ValidacaoException.class)
                .when(imagemCarroService).excluirImagem(any(UUID.class));

        var response = mvc.perform(
                delete("/api/v1/imagens/" + UUID.randomUUID())
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }
}