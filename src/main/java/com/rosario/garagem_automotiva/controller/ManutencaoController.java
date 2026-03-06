package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.dto.CadastroManutencaoDTO;
import com.rosario.garagem_automotiva.dto.ManutencaoDTO;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.service.ManutencaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping
public class ManutencaoController {

    @Autowired
    private ManutencaoService service;

    @GetMapping("/id")
    public ResponseEntity<Page<ManutencaoDTO>> listarPorCarroId(@RequestBody UUID carroId, @RequestBody Pageable pageable) {
        Page<ManutencaoDTO> manutencoes = service.listarManutencoesPorCarroId(carroId, pageable);
        return ResponseEntity.ok(manutencoes);
    }

    @GetMapping("/periodo")
    public ResponseEntity<Page<ManutencaoDTO>> listarPorPeriodo(@RequestBody LocalDate inicio, @RequestBody LocalDate fim, @RequestBody Pageable pageable) {
        Page<ManutencaoDTO> manutencoes = service.listarManutencoesPorUmPeriodo(inicio, fim, pageable);
        return ResponseEntity.ok(manutencoes);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarManutencao(CadastroManutencaoDTO dto) {
        service.cadastrarManutencao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Manutenção criada com sucesso!");
    }

    @PutMapping
    public ResponseEntity<String> atualizarManutencao(ManutencaoDTO dto) {
        try {
            service.atualizarManutencao(dto);
            return ResponseEntity.ok().body("Manutenção atualizada com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> excluirManutencao(UUID uuid) {
        try {
            service.excluirManutencao(uuid);
            return ResponseEntity.noContent().build();
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
