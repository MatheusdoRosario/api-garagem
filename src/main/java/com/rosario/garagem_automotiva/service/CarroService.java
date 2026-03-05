package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroCarroDTO;
import com.rosario.garagem_automotiva.dto.CarroDTO;
import com.rosario.garagem_automotiva.dto.CarroResponseDTO;
import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.ImagemCarro;
import com.rosario.garagem_automotiva.entity.MarcaCarro;
import com.rosario.garagem_automotiva.entity.Vendedor;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.CarroRepository;
import com.rosario.garagem_automotiva.repository.ImagemCarroRepository;
import com.rosario.garagem_automotiva.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private ImagemCarroRepository imagemCarroRepository;

    public Page<CarroResponseDTO> listarTodos(Pageable pageable) {
        Page<Carro> carros = carroRepository.findByVendido(false ,pageable);

        return carros.map(carro -> {
                    List<ImagemCarro> imagens = imagemCarroRepository.findByCarroId(carro.getId());
                    return new CarroResponseDTO(carro, imagens);
                });
    }

    public Page<CarroResponseDTO> ListarCarroPorModelo(String modelo, Pageable pageable) {
        return carroRepository.findByModelo(modelo, pageable)
                .map(carro -> {
                    List<ImagemCarro> imagens = imagemCarroRepository.findByCarroId(carro.getId());
                    return new CarroResponseDTO(carro, imagens);
                });
    }

    public Page<CarroResponseDTO> ListarCarroPorMarca(MarcaCarro marcaCarro, Pageable pageable) {
        return carroRepository.findByMarca(marcaCarro, pageable)
                .map(carro -> {
                    List<ImagemCarro> imagens = imagemCarroRepository.findByCarroId(carro.getId());
                    return new CarroResponseDTO(carro, imagens);
                });
    }

    public Page<CarroResponseDTO> ListarCarroPorAno(int ano, Pageable pageable) {
        return carroRepository.findByAno(ano, pageable)
                .map(carro -> {
                    List<ImagemCarro> imagens = imagemCarroRepository.findByCarroId(carro.getId());
                    return new CarroResponseDTO(carro, imagens);
                });
    }

    @Transactional
    public void cadastrarCarro(CadastroCarroDTO dto) {
        carroRepository.save(new Carro(dto));
    }

    @Transactional
    public void atualizarCarro(CarroDTO dto) {
        Carro carro = carroRepository.findById(dto.uuid())
                .orElseThrow(() -> new ValidacaoException("Carro não encontrado"));
        carro.atualizarCarro(dto);
    }

    @Transactional
    public void marcarComoVendido(UUID carroId, Long vendedorId) {
        Carro carro = carroRepository.findById(carroId).orElseThrow(() -> new ValidacaoException("Carro não encontrado"));
        Vendedor vendedor = vendedorRepository.findById(vendedorId).orElseThrow(() -> new ValidacaoException("Vendedor não encontrado"));

        carro.marcarComoVendido(vendedor);
    }

}
