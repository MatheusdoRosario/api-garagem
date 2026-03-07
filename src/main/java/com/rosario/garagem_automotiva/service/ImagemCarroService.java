package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroImagemCarroDTO;
import com.rosario.garagem_automotiva.entity.ImagemCarro;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.CarroRepository;
import com.rosario.garagem_automotiva.repository.ImagemCarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ImagemCarroService {

    @Autowired
    private ImagemCarroRepository imagemCarroRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Transactional
    public void salvarImagem(CadastroImagemCarroDTO dto) {
        carroRepository.findById(dto.carroId()).orElseThrow(() ->new ValidacaoException("Carro não encontrado!"));
        imagemCarroRepository.save(new ImagemCarro(dto));
    }

    @Transactional
    public void excluirImagem(UUID uuid) {
        if (!imagemCarroRepository.existsById(uuid)) {
            throw new ValidacaoException("Imagem não encontrada!");
        }
        imagemCarroRepository.deleteById(uuid);
    }

}
