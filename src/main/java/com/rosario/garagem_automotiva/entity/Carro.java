package com.rosario.garagem_automotiva.entity;

import com.rosario.garagem_automotiva.dto.CadastroCarroDTO;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double valor;

    private String modelo;

    @Enumerated(EnumType.STRING)
    private MarcaCarro marcaCarro;

    private int ano;

    private String placa;

    private LocalDate dataVenda;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Vendedor vendedor;

    public Carro() {
    }

    public Carro(CadastroCarroDTO dto) {
        this.valor = dto.valor();
        this.modelo = dto.modelo();
        this.marcaCarro = dto.marcaCarro();
        this.ano = dto.ano();
        this.placa = dto.placa();
    }

    public UUID getId() {
        return id;
    }

    public Double getValor() {
        return valor;
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

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void marcarComoVendido(Vendedor vendedor) {
        this.vendedor = vendedor;
        this.dataVenda = LocalDate.now();
    }

}
