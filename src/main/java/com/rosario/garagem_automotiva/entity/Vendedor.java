package com.rosario.garagem_automotiva.entity;

import com.rosario.garagem_automotiva.dto.CadastroVendedorDTO;
import com.rosario.garagem_automotiva.dto.VendedorDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private boolean ativo;

    @OneToMany(mappedBy = "vendedor", fetch = FetchType.LAZY)
    private List<Carro> carrosVendidos;

    public Vendedor() {
    }

    public Vendedor(CadastroVendedorDTO dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
        this.ativo = true;
    }

    public void atualizarVendedor(VendedorDTO dto) {
        if (dto.nome() != null) {
            this.nome = dto.nome();
        }
        if (dto.telefone() != null) {
            this.telefone = dto.telefone();
        }
        if (dto.ativo() != null) {
            this.ativo = dto.ativo();
        }

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

    public boolean isAtivo() {
        return ativo;
    }

    public void desativarVendedor(Vendedor vendedor) {
        this.ativo = false;
    }

    public void ativarVendedor(Vendedor vendedor) {
        this.ativo = true;
    }
}
