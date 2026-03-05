package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarroRepository extends JpaRepository<Carro, UUID> {
}
