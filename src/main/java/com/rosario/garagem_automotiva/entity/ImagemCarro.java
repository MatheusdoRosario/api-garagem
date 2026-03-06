package com.rosario.garagem_automotiva.entity;

import com.rosario.garagem_automotiva.dto.CadastroImagemCarroDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "imagensCarros")
public class ImagemCarro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String url;
    private LocalDateTime uploadDate;

    @ManyToOne
    private Carro carro;

    public ImagemCarro() {
    }

    public ImagemCarro(CadastroImagemCarroDTO dto) {
        this.url = dto.url();
        this.uploadDate = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public Carro getCarro() {
        return carro;
    }
}
