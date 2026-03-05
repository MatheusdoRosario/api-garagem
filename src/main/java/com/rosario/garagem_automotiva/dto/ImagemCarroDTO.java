package com.rosario.garagem_automotiva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record ImagemCarroDTO(@NotBlank String id,
                             @NotNull UUID carroId,
                             @NotBlank String url,
                             LocalDateTime uploadDate,
                             Map<String, String>metadata) {
}
