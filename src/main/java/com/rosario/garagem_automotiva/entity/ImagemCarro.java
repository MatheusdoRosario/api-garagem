package com.rosario.garagem_automotiva.entity;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Document(collection = "imagensCarros")
public class ImagemCarro {

    @Id
    private String id;
    private UUID carroId; // referência ao carro no Postgres
    private String url;
    private LocalDateTime uploadDate;
    private Map<String, String> metadata;

    public ImagemCarro() {
    }

    public ImagemCarro(UUID carroId, String url, Map<String, String> metadata) {
        this.carroId = carroId;
        this.url = url;
        this.metadata = metadata;
        this.uploadDate = LocalDateTime.now();
    }

    public String getId() {
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
