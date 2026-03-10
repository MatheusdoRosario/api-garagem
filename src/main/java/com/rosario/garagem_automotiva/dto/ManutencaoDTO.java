package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.Manutencao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ManutencaoDTO(@NotNull UUID id,
                            @NotNull Integer valor,
                            @NotBlank String descricao,
                            @NotNull LocalDate data,
                            @NotNull Carro carro) {

    public ManutencaoDTO(Manutencao manutencao) {
        this(manutencao.getId(), manutencao.getValor(), manutencao.getDescricao(), manutencao.getData(), manutencao.getCarro());
    }
}
