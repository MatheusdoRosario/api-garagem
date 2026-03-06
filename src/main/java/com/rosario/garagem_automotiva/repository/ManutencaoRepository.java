package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.Manutencao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ManutencaoRepository extends JpaRepository<Manutencao, UUID> {

    Page<Manutencao> findByCarroId(UUID carroId, Pageable pageable);

    Page<Manutencao> findByDataBetween(LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
}
