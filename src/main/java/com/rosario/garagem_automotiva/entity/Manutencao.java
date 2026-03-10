package com.rosario.garagem_automotiva.entity;

import com.rosario.garagem_automotiva.dto.CadastroManutencaoDTO;
import com.rosario.garagem_automotiva.dto.ManutencaoDTO;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int valor;

    private String descricao;

    private LocalDate data;

    @ManyToOne
    private Carro carro;

    public Manutencao() {
    }

    public Manutencao(CadastroManutencaoDTO dto) {
        this.valor = dto.valor();
        this.descricao = dto.descricao();
        this.data = dto.data();
        this.carro = dto.carro();
    }

    public void atualizarManutencao(ManutencaoDTO dto) {
        if (dto.valor() != null) {
            this.valor = dto.valor();
        }
        if (dto.descricao() != null) {
            this.descricao = dto.descricao();
        }
        if (dto.data() != null) {
            this.data = dto.data();
        }
        if (dto.carro() != null) {
            this.carro = dto.carro();
        }

    }

    public UUID getId() {
        return id;
    }

    public int getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public Carro getCarro() {
        return carro;
    }


}