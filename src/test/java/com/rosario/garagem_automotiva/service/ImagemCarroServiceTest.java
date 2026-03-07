package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroImagemCarroDTO;
import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.ImagemCarro;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.CarroRepository;
import com.rosario.garagem_automotiva.repository.ImagemCarroRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImagemCarroServiceTest {

    @InjectMocks
    private ImagemCarroService service;

    @Mock
    private ImagemCarroRepository imagemCarroRepository;

    @Mock
    private CarroRepository carroRepository;

    @Mock
    private CadastroImagemCarroDTO cadastroImagemCarroDTO;

    @Mock
    private ImagemCarro imagemCarro;

    @Mock
    private Carro carro;

    @Test
    void deveSalvarImagemSemErro() {
        when(carroRepository.findById(cadastroImagemCarroDTO.carroId())).thenReturn(Optional.of(carro));
        when(imagemCarroRepository.save(any(ImagemCarro.class))).thenReturn(imagemCarro);

        service.salvarImagem(cadastroImagemCarroDTO);

        then(imagemCarroRepository).should().save(any(ImagemCarro.class));
    }

    @Test
    void naoDeveSalvarImagemDeCarroInexistente() {
        when(carroRepository.findById(cadastroImagemCarroDTO.carroId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.salvarImagem(cadastroImagemCarroDTO));
    }

    @Test
    void deveExcluirImagemSemErro() {
        when(imagemCarroRepository.findById(any(UUID.class))).thenReturn(Optional.of(imagemCarro));

        service.excluirImagem(UUID.randomUUID());

        then(imagemCarroRepository).should().deleteById(any(UUID.class));
    }

    @Test
    void naoDeveExcluirImagemDeCarroInexistente() {
        when(imagemCarroRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.excluirImagem(UUID.randomUUID()));
    }
}