package com.rosario.garagem_automotiva.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    @OneToMany(mappedBy = "vendedor", fetch = FetchType.LAZY)
    private List<Carro> carrosVendidos;

    public Vendedor() {
    }

    public Vendedor(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public List<Carro> getCarrosVendidos() {
        return carrosVendidos;
    }
}
