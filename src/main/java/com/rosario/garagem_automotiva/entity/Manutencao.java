package com.rosario.garagem_automotiva.entity;

import com.rosario.garagem_automotiva.dto.CadastroManutencaoDTO;
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