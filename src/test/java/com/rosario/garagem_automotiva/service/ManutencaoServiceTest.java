package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroManutencaoDTO;
import com.rosario.garagem_automotiva.dto.ManutencaoDTO;
import com.rosario.garagem_automotiva.entity.Manutencao;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.ManutencaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManutencaoServiceTest {

    @InjectMocks
    private ManutencaoService service;

    @Mock
    private ManutencaoRepository repository;

    @Mock
    private Pageable pageable;

    @Mock
    private CadastroManutencaoDTO cadastroManutencaoDTO;

    @Mock
    private Manutencao manutencao;

    @Mock
    private ManutencaoDTO manutencaoDTO;

    @Test
    void deveListarManutencoesPorCarroId() {
        UUID carroId = UUID.randomUUID();
        Page<Manutencao> manutencaoPage = new PageImpl<>(List.of(new Manutencao()), pageable, 1);
        when(repository.findByCarroId(carroId, pageable)).thenReturn(manutencaoPage);

        service.listarManutencoesPorCarroId(carroId, pageable);

        then(repository).should().findByCarroId(carroId, pageable);
    }

    @Test
    void deveListarManutencoesPorPeriodo() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now();
        Page<Manutencao> manutencaoPage = new PageImpl<>(List.of(new Manutencao()), pageable, 1);
        when(repository.findByDataBetween(inicio, fim, pageable)).thenReturn(manutencaoPage);

        service.listarManutencoesPorUmPeriodo(inicio, fim, pageable);

        then(repository).should().findByDataBetween(inicio, fim, pageable);
    }

    @Test
    void deveCadastrarManutencaoSemErro() {
        when(repository.save(any(Manutencao.class))).thenReturn(manutencao);

        service.cadastrarManutencao(cadastroManutencaoDTO);

        then(repository).should().save(any(Manutencao.class));
    }

    @Test
    void deveAtualizarManutencaoSemErro() {
        when(repository.findById(manutencao.getId())).thenReturn(Optional.of(manutencao));

        service.atualizarManutencao(manutencaoDTO);

        then(repository).should().findById(manutencao.getId());
    }

    @Test
    void naoDeveAtualizarManutencaoInexistente() {
        when(repository.findById(manutencao.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class, () -> service.atualizarManutencao(manutencaoDTO));
    }

    @Test
    void deveExcluirManutencaoSemErro() {
        when(repository.existsById(any(UUID.class))).thenReturn(true);

        service.excluirManutencao(UUID.randomUUID());

        then(repository).should().existsById(any(UUID.class));
    }

    @Test
    void naoDeveExcluirManutencaoInexistente() {
        when(repository.existsById(any(UUID.class))).thenReturn(false);

        Assertions.assertThrows(ValidacaoException.class, () -> service.excluirManutencao(UUID.randomUUID()));
    }
}