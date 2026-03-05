package com.rosario.garagem_automotiva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

public record CadastroImagemCarroDTO(@NotNull UUID carroId,
                                     @NotBlank String url,
                                     Map<String, String> metadata) {
}
