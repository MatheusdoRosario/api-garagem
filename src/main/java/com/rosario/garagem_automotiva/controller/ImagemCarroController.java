package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.dto.CadastroImagemCarroDTO;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.service.ImagemCarroService;
import com.rosario.garagem_automotiva.service.StorageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/imagem")
public class ImagemCarroController {

    @Autowired
    private ImagemCarroService service;

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImagem(@RequestParam("carroId") UUID carroId,
                                               @RequestParam("file") @NotNull MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo inválido");
        }
        String url = storageService.salvarArquivo(file);
        service.salvarImagem(new CadastroImagemCarroDTO(carroId, url));

        return ResponseEntity.status(HttpStatus.CREATED).body("Imagem salva com sucesso! URL: " + url);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> excluirImagem(@PathVariable UUID uuid) {
        try {
            service.excluirImagem(uuid);
            return ResponseEntity.noContent().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
