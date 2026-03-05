package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
