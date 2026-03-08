package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.Vendedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    Page<Vendedor> findByAtivo(boolean ativo, Pageable pageable);

    Boolean existsByTelefone(String telefone);

    Boolean existsByTelefoneAndIdNot(String telefone, Long id);
}
