package com.rosario.garagem_automotiva.entity;

import com.rosario.garagem_automotiva.dto.CadastroImagemCarroDTO;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Document(collection = "imagensCarros")
public class ImagemCarro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID carroId;
    private String url;
    private LocalDateTime uploadDate;
    private Map<String, String> metadata;

    public ImagemCarro() {
    }

    public ImagemCarro(CadastroImagemCarroDTO dto) {
        this.carroId = dto.carroId();
        this.url = dto.url();
        this.metadata = dto.metadata();
        this.uploadDate = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getCarroId() {
        return carroId;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }
}
