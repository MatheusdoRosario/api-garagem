package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroVendedorDTO;
import com.rosario.garagem_automotiva.dto.VendedorDTO;
import com.rosario.garagem_automotiva.entity.Carro;
import com.rosario.garagem_automotiva.entity.Vendedor;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.CarroRepository;
import com.rosario.garagem_automotiva.repository.VendedorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendedorServiceTest {

    @InjectMocks
    private VendedorService service;

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private CarroRepository carroRepository;

    @Mock
    private Pageable pageable;

    @Mock
    private Page<Vendedor> vendedorPage;

    @Mock
    private Vendedor vendedor;


    @Mock
    private VendedorDTO vendedorDTO;

    @Mock
    private CadastroVendedorDTO cadastroVendedorDTO;

    @Mock
    private List<Carro> carros;

    @Test
    void deveListarTodosVendedoresSemErro() {
        when(vendedorRepository.findByAtivo(true, pageable)).thenReturn(vendedorPage);

        service.listarVendedores(pageable);

        then(vendedorRepository).should().findByAtivo(true, pageable);
    }

    @Test
    void deveListarCalculoDeVendasSemErro() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now();
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(carroRepository.findByVendedorIdAndDataVendaBetween(1L, inicio, fim)).thenReturn(carros);

        service.calcularVendas(1L, inicio, fim);

        then(vendedorRepository).should().findById(1L);
        then(carroRepository).should().findByVendedorIdAndDataVendaBetween(1L, inicio, fim);
    }

    @Test
    void naoDeveListarCalculoDeVendasParaVendedorInexistente() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.calcularVendas(1L, LocalDate.now(), LocalDate.now()));
    }

    @Test
    void deveCadastrarVendedorSemErro() {
        when(vendedorRepository.existsByTelefone(vendedor.getTelefone())).thenReturn(false);
        when(vendedorRepository.save(any(Vendedor.class))).thenReturn(vendedor);

        service.cadastrarVendedor(cadastroVendedorDTO);

        then(vendedorRepository).should().save(any(Vendedor.class));
    }

    @Test
    void naoDeveCadastrarVendedorComTelefoneCadastrado() {
        when(vendedorRepository.existsByTelefone(vendedor.getTelefone())).thenReturn(true);

       Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrarVendedor(cadastroVendedorDTO));
    }

    @Test
    void deveAtualizarVendedorSemErro() {
        when(vendedorRepository.existsByTelefoneAndIdNot(vendedor.getTelefone(), vendedorDTO.id())).thenReturn(false);
        when(vendedorRepository.findById(vendedorDTO.id())).thenReturn(Optional.of(vendedor));

        service.atualizarVendedor(vendedorDTO);

        then(vendedorRepository).should().findById(vendedorDTO.id());
    }

    @Test
    void naoDeveAtualizarVendedorComTelefoneCadastrado() {
        when(vendedorRepository.existsByTelefoneAndIdNot(vendedor.getTelefone(), vendedorDTO.id())).thenReturn(true);

       Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarVendedor(vendedorDTO));
    }

    @Test
    void naoDeveAtualizarVendedorInexistente() {
        when(vendedorRepository.existsByTelefoneAndIdNot(vendedor.getTelefone(), vendedorDTO.id())).thenReturn(false);
        when(vendedorRepository.findById(vendedorDTO.id())).thenReturn(Optional.empty());

       Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarVendedor(vendedorDTO));
    }

    @Test
    void deveDesativarVendedorSemErro() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendedor.isAtivo()).thenReturn(true);

        service.desativarVendedor(1L);

        then(vendedorRepository).should().findById(1L);
    }

    @Test
    void naoDeveDesativarVendedorDesativado() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendedor.isAtivo()).thenReturn(false);

        Assertions.assertThrows(ValidacaoException.class, () -> service.desativarVendedor(1L));
    }

    @Test
    void naoDeveDesativarVendedorInexistente() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.desativarVendedor(1L));
    }

    @Test
    void deveAtivarVendedorSemErro() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendedor.isAtivo()).thenReturn(false);

        service.ativarVendedor(1L);

        then(vendedorRepository).should().findById(1L);
    }

    @Test
    void naoDeveAtivarVendedorAtivado() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(vendedor.isAtivo()).thenReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.ativarVendedor(1L));
    }

    @Test
    void naoDeveAtivarVendedorInexistente() {
        when(vendedorRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.ativarVendedor(1L));
    }
}