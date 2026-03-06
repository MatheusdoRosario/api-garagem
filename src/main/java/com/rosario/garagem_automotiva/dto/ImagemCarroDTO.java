package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.ImagemCarro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ImagemCarroDTO(@NotNull UUID id,
                             @NotBlank String url,
                             LocalDateTime uploadDate) {
    public ImagemCarroDTO(ImagemCarro imagemCarro) {
        this(
                imagemCarro.getId(),
                imagemCarro.getUrl(),
                imagemCarro.getUploadDate()
        );
    }
}
