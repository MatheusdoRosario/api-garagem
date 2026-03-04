package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.Carro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CadastroManutencaoDTO(@NotNull int valor,
                                    @NotBlank String descricao,
                                    @NotBlank LocalDate data,
                                    @NotNull Carro carro) {
}
