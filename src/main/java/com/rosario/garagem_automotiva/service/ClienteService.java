package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroClienteDTO;
import com.rosario.garagem_automotiva.dto.ClienteDTO;
import com.rosario.garagem_automotiva.entity.Cliente;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Page<ClienteDTO> buscarClientePorNome(String nome, Pageable pageable) {
        return repository.findByNome(nome, pageable)
                .map(ClienteDTO::new);
    }

    public Page<ClienteDTO> buscarClientePorTelefone(String telefone, Pageable pageable) {
        return repository.findByTelefone(telefone, pageable)
                .map(ClienteDTO::new);
    }

    public void cadastrarCliente(CadastroClienteDTO dto) {
        if (repository.existsByTelefone(dto.telefone())) {
            throw new ValidacaoException("Telefone já cadastrado");
        }
        repository.save(new Cliente(dto));
    }

    public void atualizarCliente(ClienteDTO dto) {
        if (repository.existsByTelefone(dto.telefone())) {
            throw new ValidacaoException("Telefone já cadastrado");
        }
        Cliente cliente = repository.findById(dto.id()).orElseThrow(() -> new ValidacaoException("Cliente não encontrado!"));
        cliente.atualizarCliente(dto);
    }
}
