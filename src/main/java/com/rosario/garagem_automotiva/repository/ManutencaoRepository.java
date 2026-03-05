package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManutencaoRepository extends JpaRepository<Manutencao, UUID> {
}
