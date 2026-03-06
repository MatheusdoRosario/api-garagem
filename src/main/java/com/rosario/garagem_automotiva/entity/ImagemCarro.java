package com.rosario.garagem_automotiva.entity;

import com.rosario.garagem_automotiva.dto.CadastroImagemCarroDTO;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "imagensCarros")
public class ImagemCarro {

    @Id
    private String id;

    private UUID carroId;
    private String url;
    private LocalDateTime uploadDate;


    public ImagemCarro() {
    }

    public ImagemCarro(CadastroImagemCarroDTO dto) {
        this.carroId = dto.carroId();
        this.url = dto.url();
        this.uploadDate = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public UUID getCarroId() {
        return carroId;
    }
}
