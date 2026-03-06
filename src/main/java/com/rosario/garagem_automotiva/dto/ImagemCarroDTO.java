package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.ImagemCarro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record ImagemCarroDTO(@NotNull UUID id,
                             @NotNull UUID carroId,
                             @NotBlank String url,
                             LocalDateTime uploadDate,
                             Map<String, String>metadata) {
    public ImagemCarroDTO(ImagemCarro imagemCarro) {
        this(
                imagemCarro.getId(),
                imagemCarro.getCarroId(),
                imagemCarro.getUrl(),
                imagemCarro.getUploadDate(),
                imagemCarro.getMetadata()
        );
    }
}
