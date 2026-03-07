package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroCarroDTO;
import com.rosario.garagem_automotiva.dto.CarroDTO;
import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.ImagemCarro;
import com.rosario.garagem_automotiva.entity.MarcaCarro;
import com.rosario.garagem_automotiva.entity.Vendedor;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.CarroRepository;
import com.rosario.garagem_automotiva.repository.ImagemCarroRepository;
import com.rosario.garagem_automotiva.repository.VendedorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @InjectMocks
    private CarroService service;

    @Mock
    private CarroRepository carroRepository;

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private ImagemCarroRepository imagemCarroRepository;

    @Mock
    private Pageable pageable;

    @Mock
    private Carro carro;

    @Mock
    private Vendedor vendedor;

    @Mock
    private List<ImagemCarro> imagens;

    @Mock
    private CadastroCarroDTO cadastroCarroDTO;

    @Mock
    private CarroDTO carroDTO;

    @Test
    void deveListarTodosOsCarros() {
        when(carro.getId()).thenReturn(UUID.randomUUID());
        Page<Carro> carrosPage = new PageImpl<>(List.of(carro), pageable, 1);
        when(carroRepository.findByVendido(false, pageable)).thenReturn(carrosPage);
        when(imagemCarroRepository.findByCarroId(carro.getId())).thenReturn(imagens);

        service.listarTodos(pageable);

        then(carroRepository).should().findByVendido(false, pageable);
        then(imagemCarroRepository).should().findByCarroId(carro.getId());
    }

    @Test
    void deveListarTodosOsCarrosPorModelo() {
        String modelo = "Honda";
        when(carro.getId()).thenReturn(UUID.randomUUID());
        Page<Carro> carrosPage = new PageImpl<>(List.of(carro), pageable, 1);
        when(carroRepository.findByModeloContainingIgnoreCaseAndVendido(modelo ,false, pageable)).thenReturn(carrosPage);
        when(imagemCarroRepository.findByCarroId(carro.getId())).thenReturn(imagens);

        service.listarCarroPorModelo(modelo ,pageable);

        then(carroRepository).should().findByModeloContainingIgnoreCaseAndVendido(modelo, false, pageable);
        then(imagemCarroRepository).should().findByCarroId(carro.getId());
    }

    @Test
    void deveListarTodosOsCarrosPorMarca() {
        MarcaCarro marcaCarro = MarcaCarro.HONDA;
        when(carro.getId()).thenReturn(UUID.randomUUID());
        Page<Carro> carrosPage = new PageImpl<>(List.of(carro), pageable, 1);
        when(carroRepository.findByMarcaCarroAndVendido(marcaCarro ,false, pageable)).thenReturn(carrosPage);
        when(imagemCarroRepository.findByCarroId(carro.getId())).thenReturn(imagens);

        service.listarCarroPorMarca(marcaCarro ,pageable);

        then(carroRepository).should().findByMarcaCarroAndVendido(marcaCarro, false, pageable);
        then(imagemCarroRepository).should().findByCarroId(carro.getId());
    }

    @Test
    void deveListarTodosOsCarrosPorAno() {
        int ano = 2026;
        when(carro.getId()).thenReturn(UUID.randomUUID());
        Page<Carro> carrosPage = new PageImpl<>(List.of(carro), pageable, 1);
        when(carroRepository.findByAnoAndVendido(ano ,false, pageable)).thenReturn(carrosPage);
        when(imagemCarroRepository.findByCarroId(carro.getId())).thenReturn(imagens);

        service.listarCarroPorAno(ano ,pageable);

        then(carroRepository).should().findByAnoAndVendido(ano, false, pageable);
        then(imagemCarroRepository).should().findByCarroId(carro.getId());
    }

    @Test
    void deveCadastrarCarroSemErro() {
        when(carroRepository.save(any(Carro.class))).thenReturn(carro);

        service.cadastrarCarro(cadastroCarroDTO);

        then(carroRepository).should().save(any(Carro.class));
    }

    @Test
    void deveAtualizarCarroSemErro() {
        when(carroRepository.findById(carroDTO.uuid())).thenReturn(Optional.of(carro));

        service.atualizarCarro(carroDTO);

        then(carroRepository).should().findById(carroDTO.uuid());
    }

    @Test
    void naoDeveAtualizarCarroInexistente() {
        when(carroRepository.findById(carroDTO.uuid())).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarCarro(carroDTO));
    }

    @Test
    void deveMarcarCarroComoVendidoSemErro() {
        when(carroRepository.findById(any(UUID.class))).thenReturn(Optional.of(carro));
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));

        service.marcarComoVendido(UUID.randomUUID(), 1L);

        then(carroRepository).should().findById(any(UUID.class));
        then(vendedorRepository).should().findById(1L);
    }

    @Test
    void naoDeveMarcarCarroComoVendidoCarroInexistente() {
        when(carroRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.marcarComoVendido(UUID.randomUUID(), 1L));
    }

    @Test
    void naoDeveMarcarCarroComoVendidoVendedorInexistente() {
        when(carroRepository.findById(any(UUID.class))).thenReturn(Optional.of(carro));
        when(vendedorRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.marcarComoVendido(UUID.randomUUID(), 1L));
    }
}