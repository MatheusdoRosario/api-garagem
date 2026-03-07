package com.rosario.garagem_automotiva.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void deveSalvarImagemSemErro() throws IOException {
        StorageService service = new StorageService() {
            private final Path root = tempDir;

            public String salvarArquivo(MockMultipartFile file) throws IOException {
                if (!Files.exists(root)) {
                    Files.createDirectories(root);
                }
                String nomeArquivo = "teste-" + file.getOriginalFilename();
                Path destino = root.resolve(nomeArquivo);
                Files.copy(file.getInputStream(), destino);
                return destino.toString();
            }
        };
        MockMultipartFile file = new MockMultipartFile(
                "file", "exemplo.txt", "text/plain", "conteúdo de teste".getBytes()
        );
        String caminho = service.salvarArquivo(file);

        Path destino = Path.of(caminho);
        assertTrue(Files.exists(destino));
        assertEquals("conteúdo de teste", Files.readString(destino));
    }

    void naoDeveSalvarArquivoComErroDeInputStream() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "falha.txt",
                "text/plain",
                new InputStream() {
                    @Override
                    public int read() throws IOException {
                        throw new IOException("Erro simulado no InputStream");
                    }
                }
        );

        StorageService service = new StorageService() {
            private final Path root = tempDir;
            public String salvarArquivo(MockMultipartFile f) throws IOException {
                return super.salvarArquivo(f);
            }
        };

        assertThrows(IOException.class, () -> service.salvarArquivo(file));
    }


}