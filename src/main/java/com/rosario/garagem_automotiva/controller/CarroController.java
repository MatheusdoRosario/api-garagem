package com.rosario.garagem_automotiva.controller;

import com.rosario.garagem_automotiva.dto.CadastroCarroDTO;
import com.rosario.garagem_automotiva.dto.CarroDTO;
import com.rosario.garagem_automotiva.dto.CarroResponseDTO;
import com.rosario.garagem_automotiva.entity.MarcaCarro;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.service.CarroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carro")
public class CarroController {

    @Autowired
    private CarroService service;

    @GetMapping
    public ResponseEntity<Page<CarroResponseDTO>> listarTodos(@RequestBody Pageable pageable) {
        Page<CarroResponseDTO> carrosDisponiveis = service.listarTodos(pageable);
        return ResponseEntity.ok(carrosDisponiveis);
    }

    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<Page<CarroResponseDTO>> listarPorModelo(@PathVariable String modelo, @RequestBody Pageable pageable) {
            Page<CarroResponseDTO> carrosPorModelo = service.listarCarroPorModelo(modelo, pageable);
            return ResponseEntity.ok(carrosPorModelo);
    }

    @GetMapping("/marca/{marcaCarro}")
    public ResponseEntity<Page<CarroResponseDTO>> listarPorMarca(@PathVariable MarcaCarro marcaCarro, @RequestBody Pageable pageable) {
            Page<CarroResponseDTO> carrosPorMarca = service.listarCarroPorMarca(marcaCarro, pageable);
            return ResponseEntity.ok(carrosPorMarca);
    }

    @GetMapping("/ano/{ano}")
    public ResponseEntity<Page<CarroResponseDTO>> listarPorAno(@PathVariable int ano, @RequestBody Pageable pageable) {
            Page<CarroResponseDTO> carrosPorAno = service.listarCarroPorAno(ano, pageable);
            return ResponseEntity.ok(carrosPorAno);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarCarro(@RequestBody @Valid CadastroCarroDTO dto) {
        service.cadastrarCarro(dto);
        return ResponseEntity.ok().body("Carro cadastrado com sucesso!");
    }

    @PutMapping
    public ResponseEntity<String> atualizarCarro(@RequestBody @Valid CarroDTO dto) {
        try {
            service.atualizarCarro(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Carro atualizado com sucesso!");
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/vender")
    public ResponseEntity<String> marcarComoVendido(@RequestBody @Valid UUID carroId, @RequestBody @Valid Long vendedorId) {
        try {
            service.marcarComoVendido(carroId, vendedorId);
            return ResponseEntity.ok().body("Carro vendido!");
        } catch (ValidacaoException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
