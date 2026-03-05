package com.rosario.garagem_automotiva.dto;

import com.rosario.garagem_automotiva.entity.Vendedor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VendedorDTO(@NotNull Long id,
                          @NotBlank String nome,
                          @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$") String telefone,
                          @NotNull Boolean ativo) {

    public VendedorDTO(Vendedor vendedor) {
        this(vendedor.getId(), vendedor.getNome(), vendedor.getNome(), vendedor.isAtivo());
    }
}
