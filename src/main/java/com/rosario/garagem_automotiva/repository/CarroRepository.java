package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.MarcaCarro;
import com.rosario.garagem_automotiva.entity.Vendedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CarroRepository extends JpaRepository<Carro, UUID> {

    Page<Carro> findByModelo(String modelo, Pageable pageable);

    Page<Carro> findByMarca(MarcaCarro marcaCarro, Pageable pageable);

    Page<Carro> findByAno(int ano, Pageable pageable);

    Page<Carro> findByVendido(boolean vendido, Pageable pageable);

    List<Carro> findByVendedorAndDataVendaBetween(Vendedor vendedor, LocalDateTime inicio, LocalDateTime fim);


}
