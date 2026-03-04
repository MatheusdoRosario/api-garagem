package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.MarcaCarro;

import java.util.UUID;

public record CadastroCarroDTO(Double valor,
                               String modelo,
                               MarcaCarro marcaCarro,
                               int ano,
                               String placa) {
}
