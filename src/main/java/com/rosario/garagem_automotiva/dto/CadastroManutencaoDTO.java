package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.Carro;

import java.time.LocalDate;
import java.util.UUID;

public record ManutencaoDTO(UUID id,
                            int valor,
                            String descricao,
                            LocalDate data,
                            Carro carro) {
}
