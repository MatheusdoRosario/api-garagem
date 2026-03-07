package com.rosario.garagem_automotiva.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void deveSalvarImagemSemErro() throws IOException {
        StorageService service = new StorageService(tempDir);
        MockMultipartFile file = new MockMultipartFile(
                "file", "exemplo.txt", "text/plain", "conteúdo de teste".getBytes()
        );
        String caminho = service.salvarArquivo(file);
        String nomeArquivo = caminho.substring(caminho.lastIndexOf("/") + 1);
        Path destino = tempDir.resolve(nomeArquivo);

        assertTrue(Files.exists(destino));
        assertEquals("conteúdo de teste", Files.readString(destino));

    }

    @Test
    void naoDeveSalvarArquivoComErroDeInputStream() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("falha.txt");
        when(file.getInputStream()).thenThrow(new IOException("Erro simulado"));

        StorageService service = new StorageService(tempDir);

        assertThrows(IOException.class, () -> service.salvarArquivo(file));    }


}