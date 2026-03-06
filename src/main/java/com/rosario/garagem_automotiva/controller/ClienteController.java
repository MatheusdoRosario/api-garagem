package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.dto.CadastroClienteDTO;
import com.rosario.garagem_automotiva.dto.ClienteDTO;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Page<ClienteDTO>> listarClientePorNome(@PathVariable String nome, Pageable pageable) {
        Page<ClienteDTO> clientes = service.listarClientesPorNome(nome, pageable);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/telefone/{telefone}")
    public ResponseEntity<Page<ClienteDTO>> listarClientePorTelefone(@PathVariable String telefone, Pageable pageable) {
        Page<ClienteDTO> clientes = service.listarClientesPorTelefone(telefone, pageable);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarCliente(@RequestBody @Valid CadastroClienteDTO dto) {
        try {
            service.cadastrarCliente(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizarCliente(@RequestBody @Valid ClienteDTO dto) {
        try {
            service.atualizarCliente(dto);
            return ResponseEntity.ok().body("Cliente atualizado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
