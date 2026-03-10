package com.rosario.garagem_automotiva.entity;

import com.rosario.garagem_automotiva.dto.CadastroClienteDTO;
import com.rosario.garagem_automotiva.dto.ClienteDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    public Cliente() {
    }

    public Cliente(CadastroClienteDTO dto) {
        this.nome = dto.nome();
        this.telefone = dto.telefone();
    }

    public void atualizarCliente(ClienteDTO dto) {
        if (dto.nome() != null) {
            this.nome = dto.nome();
        }
        if (dto.nome() != null) {
            this.telefone = dto.telefone();
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
}
