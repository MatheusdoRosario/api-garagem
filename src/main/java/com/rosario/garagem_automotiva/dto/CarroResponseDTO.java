package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.ImagemCarro;

import java.util.List;

public record CarroResponseDTO(Carro carro, List<ImagemCarro> imagens) {
}
