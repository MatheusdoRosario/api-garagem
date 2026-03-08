package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroClienteDTO;
import com.rosario.garagem_automotiva.dto.ClienteDTO;
import com.rosario.garagem_automotiva.entity.Cliente;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService service;

    @Mock
    private ClienteRepository repository;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<Cliente> clientePage;

    @Mock
    private Cliente cliente;

    @Mock
    private ClienteDTO clienteDTO;

    @Mock
    private CadastroClienteDTO cadastroClienteDTO;

    @Test
    void deveListarClientesPorNome() {
        String nome = "Matheus";
        when(repository.findByNomeContainingIgnoreCase(nome, pageable)).thenReturn(clientePage);

        service.listarClientesPorNome(nome, pageable);

        then(repository).should().findByNomeContainingIgnoreCase(nome, pageable);
    }

    @Test
    void deveListarClientesPorTelefone() {
        String telefone = "22555554444";
        when(repository.findByTelefoneContaining(telefone, pageable)).thenReturn(clientePage);

        service.listarClientesPorTelefone(telefone, pageable);

        then(repository).should().findByTelefoneContaining(telefone, pageable);
    }

    @Test
    void deveCadastrarClienteSemErro() {
        when(repository.existsByTelefone(cadastroClienteDTO.telefone())).thenReturn(false);
        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        service.cadastrarCliente(cadastroClienteDTO);

        then(repository).should().save(any(Cliente.class));
    }

    @Test
    void naoDeveCadastrarClienteComTelefoneCadastrado() {
        when(repository.existsByTelefone(cadastroClienteDTO.telefone())).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrarCliente(cadastroClienteDTO));
    }

    @Test
    void deveAtualizarClienteSemErro() {
        when(repository.existsByTelefoneAndIdNot(clienteDTO.telefone(), clienteDTO.id())).thenReturn(false);
        when(repository.findById(clienteDTO.id())).thenReturn(Optional.of(cliente));

        service.atualizarCliente(clienteDTO);

        then(repository).should().findById(clienteDTO.id());
    }

    @Test
    void naoDeveAtualizarClienteComTelefoneCadastrado() {
        when(repository.existsByTelefoneAndIdNot(clienteDTO.telefone(), clienteDTO.id())).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarCliente(clienteDTO));
    }

    @Test
    void naoDeveAtualizarClienteInexistente() {
        when(repository.existsByTelefoneAndIdNot(clienteDTO.telefone(), clienteDTO.id())).thenReturn(false);
        when(repository.findById(clienteDTO.id())).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarCliente(clienteDTO));
    }
}