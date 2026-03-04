package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.MarcaCarro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CarroDTO(@NotNull UUID uuid,
                       @NotBlank String modelo,
                       @NotNull MarcaCarro marcaCarro,
                       @NotNull int ano,
                       @NotBlank String placa) {
}
