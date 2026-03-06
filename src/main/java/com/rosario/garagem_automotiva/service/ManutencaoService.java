package com.rosario.garagem_automotiva.service;

import com.rosario.garagem_automotiva.dto.CadastroManutencaoDTO;
import com.rosario.garagem_automotiva.dto.ManutencaoDTO;
import com.rosario.garagem_automotiva.entity.Manutencao;
import com.rosario.garagem_automotiva.exception.ValidacaoException;
import com.rosario.garagem_automotiva.repository.ManutencaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository repository;

    public Page<ManutencaoDTO> listarManutencoesPorCarroId(UUID carroId, Pageable pageable) {
        return repository.findByCarroId(carroId, pageable)
                .map(ManutencaoDTO::new);
    }

    public Page<ManutencaoDTO> listarManutencoesPorUmPeriodo(LocalDate inicio, LocalDate fim, Pageable pageable) {
        return repository.findByDataBetween(inicio.atStartOfDay(), fim.atTime(23,59), pageable)
                .map(ManutencaoDTO::new);
    }

    @Transactional
    public void cadastrarManutencao(CadastroManutencaoDTO dto) {
        repository.save(new Manutencao(dto));
    }

    @Transactional
    public void atualizarManutencao(ManutencaoDTO dto){
        Manutencao manutencao = repository.findById(dto.id()).orElseThrow(() -> new ValidacaoException("Manutencão não encontrada!"));
        manutencao.atualizarManutencao(dto);
    }

    @Transactional
    public void excluirManutencao(UUID uuid) {
        if (!repository.existsById(uuid)) {
            throw new ValidacaoException("Manutenção não encontrada!");
        }
        repository.deleteById(uuid);
    }
}
