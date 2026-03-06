package com.rosario.garagem_automotiva.dto;

import java.util.UUID;

public record VenderCarroDTO(UUID carroId,
                             Long vendedorId
) {
}
