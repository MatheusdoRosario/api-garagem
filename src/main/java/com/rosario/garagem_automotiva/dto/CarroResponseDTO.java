package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.MarcaCarro;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CarroResponseDTO(UUID uuid,
                               BigDecimal valor,
                               String modelo,
                               MarcaCarro marcaCarro,
                               int ano,
                               List<ImagemCarroDTO> imagens) {

    public CarroResponseDTO(Carro carro) {
        this(
                carro.getId(),
                carro.getValor(),
                carro.getModelo(),
                carro.getMarcaCarro(),
                carro.getAno(),
                carro.getImagens().stream()
                        .map(ImagemCarroDTO::new)
                        .toList()
        );
    }
}
