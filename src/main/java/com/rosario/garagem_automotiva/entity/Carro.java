package com.rosario.garagem_automotiva.entity;

import com.rosario.garagem_automotiva.dto.CadastroCarroDTO;
import com.rosario.garagem_automotiva.dto.CarroDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private BigDecimal valor;

    private String modelo;

    @Enumerated(EnumType.STRING)
    private MarcaCarro marcaCarro;

    private int ano;

    private String placa;

    private LocalDate dataVenda;

    private Boolean vendido;

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
        this.vendido = false;
    }

    public void atualizarCarro(CarroDTO dto) {
        if (dto.valor() != null) {
            this.valor = dto.valor();
        }
        if (dto.modelo() != null) {
            this.modelo = dto.modelo();
        }
        if (dto.marcaCarro() != null) {
            this.marcaCarro = dto.marcaCarro();
        }
        if (dto.ano() != null) {
            this.ano = dto.ano();
        }
        if (dto.placa() != null) {
            this.placa = dto.placa();
        }
        if (dto.vendido() != null) {
            this.vendido = dto.vendido();
        }
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getValor() {
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

    public Boolean getVendido() {
        return vendido;
    }

    public void marcarComoVendido(Vendedor vendedor) {
        this.vendedor = vendedor;
        this.dataVenda = LocalDate.now();
        this.vendido = true;
    }

}
