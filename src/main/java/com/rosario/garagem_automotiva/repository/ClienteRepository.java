package com.rosario.garagem_automotiva.repository;

import com.rosario.garagem_automotiva.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Cliente> findByTelefoneContaining(String telefone, Pageable pageable);

    Boolean existsByTelefone(String telefone);

    Boolean existsByTelefoneAndIdNot(String telefone, Long id);
}
