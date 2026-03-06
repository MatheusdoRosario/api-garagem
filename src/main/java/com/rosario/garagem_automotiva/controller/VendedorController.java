package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.dto.CadastroVendedorDTO;
import com.rosario.garagem_automotiva.dto.VendasResumoDTO;
import com.rosario.garagem_automotiva.dto.VendedorDTO;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.service.VendedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/vendedor")
public class VendedorController {

    @Autowired
    private VendedorService service;

    @GetMapping
    public ResponseEntity<Page<VendedorDTO>> listarVendedores(Pageable pageable) {
        Page<VendedorDTO> vendedores = service.listarVendedores(pageable);
        return ResponseEntity.ok(vendedores);
    }

    @GetMapping("/vendas")
    public ResponseEntity<VendasResumoDTO> listarResumoDeVendasPorVendedor(@RequestBody Long vendedorId,
                                                                           @RequestBody LocalDate inicio,
                                                                           @RequestBody LocalDate fim) {
        try {
            VendasResumoDTO vendasResumo = service.calcularVendas(vendedorId, inicio, fim);
            return ResponseEntity.ok(vendasResumo);
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrarVendedor(@RequestBody @Valid CadastroVendedorDTO dto) {
        try {
            service.cadastrarVendedor(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Vendedor criado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizarVendedor(@RequestBody @Valid VendedorDTO dto) {
        try {
            service.atualizarVendedor(dto);
            return ResponseEntity.ok().body("Vendedor atualizado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativar")
    public ResponseEntity<String> desativarVendedor(@RequestBody Long id) {
        try {
            service.desativarVendedor(id);
            return ResponseEntity.ok().body("Vendedor desativado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/ativar")
    public ResponseEntity<String> ativarVendedor(@RequestBody Long id) {
        try {
            service.ativarVendedor(id);
            return ResponseEntity.ok().body("Vendedor ativado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
