package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
}
