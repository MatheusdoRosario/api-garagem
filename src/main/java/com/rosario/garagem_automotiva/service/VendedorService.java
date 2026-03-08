package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroVendedorDTO;
import com.rosario.garagem_automotiva.dto.VendasResumoDTO;
import com.rosario.garagem_automotiva.dto.VendedorDTO;
import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.Vendedor;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.CarroRepository;
import com.rosario.garagem_automotiva.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private CarroRepository carroRepository;

    public Page<VendedorDTO> listarVendedores(Pageable pageable) {
        return vendedorRepository.findByAtivo(true, pageable)
                .map(VendedorDTO::new);
    }

    public VendasResumoDTO calcularVendas(Long vendedorId, LocalDate inicio, LocalDate fim) {
        vendedorRepository.findById(vendedorId)
                .orElseThrow(() -> new ValidacaoException("Vendedor não encontrado"));

        List<Carro> carrosVendidos = carroRepository
                .findByVendedorIdAndDataVendaBetween(vendedorId, inicio, fim);

        int quantidade = carrosVendidos.size();
        BigDecimal valorTotal = carrosVendidos.stream()
                .map(Carro::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new VendasResumoDTO(quantidade, valorTotal);
    }

    @Transactional
    public void cadastrarVendedor(CadastroVendedorDTO dto) {
        if (vendedorRepository.existsByTelefone(dto.telefone())){
            throw new ValidacaoException("Telefone já cadastrado!");
        }
        vendedorRepository.save(new Vendedor(dto));
    }

    @Transactional
    public void atualizarVendedor(VendedorDTO dto) {
        if (vendedorRepository.existsByTelefoneAndIdNot(dto.telefone(), dto.id())){
            throw new ValidacaoException("Telefone já cadastrado!");
        }
        Vendedor vendedor = vendedorRepository.findById(dto.id()).orElseThrow(() -> new ValidacaoException("Vendedor não encontrado"));
        vendedor.atualizarVendedor(dto);
    }

    @Transactional
    public void desativarVendedor(Long id) {
        Vendedor vendedor = vendedorRepository.findById(id).orElseThrow(() -> new ValidacaoException("Vendedor não encontrado"));
        if (!vendedor.isAtivo()){
            throw new ValidacaoException("Vendedor já desativado!");
        }
        vendedor.desativarVendedor(vendedor);
    }

    @Transactional
    public void ativarVendedor(Long id) {
        Vendedor vendedor = vendedorRepository.findById(id).orElseThrow(() -> new ValidacaoException("Vendedor não encontrado"));
        if (vendedor.isAtivo()){
            throw new ValidacaoException("Vendedor já ativado!");
        }
        vendedor.ativarVendedor(vendedor);
    }

}
