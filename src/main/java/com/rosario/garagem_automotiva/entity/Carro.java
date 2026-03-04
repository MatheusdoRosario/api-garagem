package com.rosario.garagem_automotiva.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private String modelo;

    @Enumerated(EnumType.STRING)
    private MarcaCarro marcaCarro;

    private int ano;

    private String placa;

    @ManyToOne
    private Cliente cliente;

    public Carro() {
    }

    public Carro(String nome, String modelo, MarcaCarro marcaCarro, int ano, String placa) {
        this.nome = nome;
        this.modelo = modelo;
        this.marcaCarro = marcaCarro;
        this.ano = ano;
        this.placa = placa;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getModelo() {
        return modelo;
    }

    public MarcaCarro getMarcaCarro() {
        return marcaCarro;
    }

    public int getAno() {
        return ano;
    }

    public String getPlaca() {
        return placa;
    }
}
