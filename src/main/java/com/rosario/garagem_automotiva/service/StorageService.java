package com.rosario.garagem_automotiva.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

    private final Path root = Paths.get("uploads");

    public String salvarArquivo(MultipartFile file) throws IOException {
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
        String nomeArquivo = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path destino = root.resolve(nomeArquivo);
        Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + nomeArquivo;
    }

}
