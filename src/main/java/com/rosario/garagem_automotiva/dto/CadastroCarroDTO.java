package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.MarcaCarro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CadastroCarroDTO(@NotNull BigDecimal valor,
                               @NotBlank String modelo,
                               @NotNull MarcaCarro marcaCarro,
                               @NotNull int ano,
                               @NotBlank String placa) {
}
