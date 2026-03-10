package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.ImagemCarro;
import com.rosario.garagem_automotiva.entity.MarcaCarro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CarroDTO(@NotNull UUID uuid,
                       @NotNull BigDecimal valor,
                       @NotBlank String modelo,
                       @NotNull MarcaCarro marcaCarro,
                       @NotNull Integer ano,
                       @NotBlank String placa,
                       Boolean vendido,
                       List<ImagemCarroDTO> imagens) {

    public CarroDTO(Carro carro, List<ImagemCarro> imagensMongo) {
        this(
                carro.getId(),
                carro.getValor(),
                carro.getModelo(),
                carro.getMarcaCarro(),
                carro.getAno(),
                carro.getPlaca(),
                carro.getVendido(),
                imagensMongo.stream()
                        .map(ImagemCarroDTO::new)
                        .toList()
        );
    }
}
