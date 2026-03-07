package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.MarcaCarro;
import com.rosario.garagem_automotiva.entity.Vendedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CarroRepository extends JpaRepository<Carro, UUID> {

    Page<Carro> findByModeloContainingIgnoreCaseAndVendido(String modelo, boolean vendido, Pageable pageable);

    Page<Carro> findByMarcaCarroAndVendido(MarcaCarro marcaCarro, boolean vendido, Pageable pageable);

    Page<Carro> findByAnoAndVendido(int ano, boolean vendido,Pageable pageable);

    Page<Carro> findByVendido(boolean vendido, Pageable pageable);

    List<Carro> findByVendedorIdAndDataVendaBetween(Long id, LocalDate inicio, LocalDate fim);


}
